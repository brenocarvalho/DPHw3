package server;

import java.util.List;

import model.Task;
import network.*;

public class Agent{
	String name;
	IClient client;
	List<Task> tasks;
	
	public Agent(String name, IClient client){
		this.name = name;
		this.client = client;
		this.client.setAgent(this);
	}

	public synchronized void enqueue(Task task){
		tasks.add(task);
	}

	public String getName(){ return name;}
	
	public void execute(TaskMessage command){
		Task task = command.getTask();
		long begin = System.currentTimeMillis(), end;
		try {
			task.run();
		} catch (Exception e) {
			end = System.currentTimeMillis();
			client.receiveFailure(new FailMessage(getName(), task,end-begin));
		}
		end = System.currentTimeMillis();
		client.receiveSuccess(new SuccessMessage(getName(), task,end-begin));
	}
}
