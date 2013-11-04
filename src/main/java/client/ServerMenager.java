package client;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fakeNetwork.InstantTimeServer;

import parsing.Parser;

import network.*;
import network.Constants.SERVER_STATUS;

import model.Graph;
import model.Node;
import model.Task;

public class ServerMenager{
	private List<IServer> servers;
	private List<Task> toSend, processing;
	private Map<Task, Node> nodes;
	private IServer localServer;
	private Scheduler scheduler;
	
	public ServerMenager(Scheduler scheduler){
		this.scheduler = scheduler;
		nodes = new HashMap<Task, Node>();
		servers = new LinkedList<IServer>();
		toSend = new LinkedList<Task>();
		processing = new LinkedList<Task>();
		localServer = new fakeNetwork.InstantTimeServer("localServer", this);
	}
	
	public void addServer(IServer server){
		if(!servers.contains(server)){
			servers.add(server);
		}
	}
	
	public void removeServer(IServer server){
		servers.remove(server);
	}
	
	public int getNumActiveServers(){
		int count= 0;
		for(IServer server: servers){
			if(server.getStatus() == SERVER_STATUS.Avaible){ count ++;}
		}
		if(count == 0 && localServer.getStatus() == SERVER_STATUS.Avaible){ count++;}
		return count;
	}
	
	public int getNumServers(){
		return servers.size();
	}
	
	public boolean hasMessage(){
		return !scheduler.allTasksCompleted();
	}
	
	public void loadTasks(){
		Task task;
		for(Node node: scheduler.getOrphans()){
			task = node.getTask();
			if(!toSend.contains(task) && !processing.contains(task)){
				toSend.add(task);
				nodes.put(task, node);
			}
		}
	}
	
	public void removeProcessingTask(Task task){
		System.out.print(task.toString()+"\n");
		processing.remove(task);
		Node node = nodes.remove(task);
		if(node != null)
			scheduler.removeTask(node);
	}
	
	public IServer getFreeServer() throws Exception{
		for(int i = 0; i < Constants.MAX_WAITING_ITERATIONS; i++){
			for(IServer candidate: servers){
				if(candidate.getStatus() == SERVER_STATUS.Avaible){
					return candidate;
				}
			}
			if(localServer.getStatus() == SERVER_STATUS.Avaible){
				return localServer;
			}
			Thread.sleep(Constants.MAX_WAITING_SERVER_TIME);
		}
		throw new Exception(); //TODO specify exception 
	}
	
	public void delegate() throws Exception{
		IServer server = getFreeServer();//TODO handle this possible exception
		Task task;
		loadTasks();
		if(!toSend.isEmpty()){
			task = toSend.remove(0);
		}else{
			if(!processing.isEmpty()){
				task = processing.remove(0);
			}else{
				return;
			}
		}
		processing.add(task);
		System.out.println("Sending task");
		server.sendRequest(new TaskMessage("client", task));
	}

	/*
	public static void main(String[] args) throws Exception{
		if(args.length < 2){
			throw new Exception(String.format("Usage: java %s [file_name] [server_adress]+", args[0]));
		}
		Parser parser = new Parser();
		parser.parse(args[0]);
		Graph g = parser.getGraph();
		Scheduler sc = new Scheduler(g);
		ServerMenager menager = new ServerMenager(sc);
		for(int i = 2; i < args.length; i++){
			new InstantTimeServer(String.format("server %d <%s>", i, args[i]), menager);
		}
		System.out.print("starting execution");

		while(menager.hasMessage()){
			menager.delegate();
		}
		System.out.print("Done");
	}
	*/
}
