package model;

import java.util.LinkedList;
import java.util.Observable;
import java.util.List;

public class SThread extends Thread{

	private List<Node> tasks;
	private Status status;
	private List<Node> lock;
	private Scheduler scheduler;

	public void setLock(List<Node> lock){
		this.lock = lock;
	}
	
	public SThread(Scheduler scheduler){
		status = Status.SUCCESS;
		tasks = new LinkedList();
		this.scheduler = scheduler;
	}
	
	public void addTask(Node task){
		tasks.add(task);
	}
	
	public Status getStatus(){return status;}
	
	public boolean haveTaksToRun(){ return !tasks.isEmpty();}
	
	@Override
	public void run() {
		status = Status.RUNNING;
		Node n = tasks.get(0);
		Task t = n.getTask();
		tasks.remove(0);
		try {
			status = t.run();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			synchronized(lock){
				scheduler.addFreeThread(this);
				lock.add(n);
				lock.notifyAll();
			}
		}
	}

}
