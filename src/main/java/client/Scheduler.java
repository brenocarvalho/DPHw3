package client;

import java.util.LinkedList;
import java.util.List;



import model.*;

public class Scheduler {
	private List<LogEntry> log;
	private Graph graph;
	
	public Node[] getOrphans(){
		return graph.getOrphans();
	};
	
	public void removeTask(Node node){
		graph.removeNode(node);
	};
	
	public boolean allTasksCompleted(){ return graph.getNumNodes() == 0;}
	
	public void updateLog(Task task, IServer server, long elapsedTime){
		log.add(new LogEntry(task, server.toString(), elapsedTime));
	}
	
	public Graph getGraph(){
		return graph;
	}
	
	public Scheduler(Graph g){
		log = new LinkedList();
		this.graph = g;
	}
	
	@Override
	public String toString(){
		String out = "Schedule:\n";
		for(LogEntry entry: log){
			out += entry.toString()+"\n";
		}
		return out;
	}
}
