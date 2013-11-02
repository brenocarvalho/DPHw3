package server;

import model.Task;
import network.*;

public class Agent {
	String name;
	IClient client;
	
	public Agent(String name, IClient client){
		this.name = name;
		this.client = client;
		//this.server = server;
	}


	public String getName(){ return name;}
	
	public void execute(TaskMessage<IClient> command){
		Task task = command.getTask();
		long begin = System.currentTimeMillis(), end;
		try {
			task.run();
		} catch (Exception e) {
			end = System.currentTimeMillis();
			//server.informFail(master, new FailMessage(getName(), task,end-begin));
		}
		end = System.currentTimeMillis();
		//server.informSuccess(master, new SuccessMessage(getName(), task,end-begin));
	}
	
	public static void main(String[] args){
		String name;
		if(args.length < 2){
			name = "localServer";
		}else{
			name = args[1];
		}
		IClient client = null; //TODO Use a actual client here
		Agent bond = new Agent(name, client);
	}
}
