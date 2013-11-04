package fakeNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import network.*;
import server.Agent;
import server.IClient;
import model.Task;

public class RandomClient implements IClient{
	private Agent agent;
	private String name;
	private List<Task> tasks;
	
	public RandomClient(String name){
		this.name = name;
		this.tasks = new ArrayList();
	}
	
	public void receiveSuccess(SuccessMessage success) {
		Task task = success.getTask();
		tasks.remove(task);
	}

	public void receiveFailure(FailMessage fail) {
		Task task = fail.getTask();
		tasks.remove(task);
	}

	public void receiveStatus(ServerStatusMessage status) {}

	public void setAgent(Agent agent) {
		this.agent = agent;		
	}
	
	public String toString(){
		return "Random Client: "+name;
	}
	
	public boolean sendTasks(int qtd) throws Exception{
		Random generator = new Random();
		Task task;
		for(int i = 0; i < qtd; i++){
			task = new Task("Sleep", 100);
			agent.execute(new TaskMessage(this.toString(), task));
			System.out.print("Sleep\n");
			Thread.sleep(generator.nextInt(100)+50);
		}
		return tasks.size() == 0;
	}
}
