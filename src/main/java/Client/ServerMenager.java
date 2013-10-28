package Client;

import java.util.LinkedList;
import java.util.List;

import model.Task;

public class ServerMenager {
	List<IServer> servers;
	
	public ServerMenager(){
		servers = new LinkedList<IServer>();
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
		// TODO
		return 0;
	}
	
	public int getNumServers(){
		return servers.size();
	}
	
	public void delegate(Task task){
		// TODO
	}
}
