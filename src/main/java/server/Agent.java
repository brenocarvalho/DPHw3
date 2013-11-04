package server;

import model.Task;
import network.*;

public class Agent {
	String name;
	IClient client;
	
	public Agent(String name, IClient client){
		this.name = name;
		this.client = client;
		this.client.setAgent(this);
	}


	public String getName(){ return name;}
	
	public void execute(TaskMessage<IClient> command){
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
	
	public static void main(String[] args){
		String name;
		if(args.length < 2){
			name = "localServer";
		}else{
			name = args[1];
		}
		IClient client = null; //TODO Use an actual client here
		Agent local = new Agent(name, client);
	}
}
