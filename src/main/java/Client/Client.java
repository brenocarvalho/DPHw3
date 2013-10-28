package Client;

import model.Graph;
import model.Node;

public class Client{

	ServerMenager service;
	Scheduler scheduler;
	
	public void executeTasks(Graph graph) throws Exception{
		Node[] orphans = scheduler.getOrphans();
		Node node;
		while(orphans.length > 0){
			node = orphans[0];
			service.delegate(node.getTask());
		}
	}
	
}
