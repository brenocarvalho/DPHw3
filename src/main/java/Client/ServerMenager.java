package Client;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Network.*;

import model.Task;

public class ServerMenager{
	private List<IServer> servers;
	private IServer localServer;
	
	public ServerMenager(){
		servers = new LinkedList<IServer>();
		localServer = new FakeServer.InstantTimeServer();
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
			if(server.isActive()){ count ++;}
		}
		if(count == 0 && localServer.isActive()){ count++;}
		return count;
	}
	
	public int getNumServers(){
		return servers.size();
	}
	
	public void delegate(Task task) throws Exception{
		for(IServer server: servers){
			if(server.isActive()){
				server.acceptRequest(new TaskMessage(this, task));
				return;
			}
		}
		
		if(localServer.isActive()){
			localServer.acceptRequest(new TaskMessage(this, task));
			return;
		}
		throw new Exception("Message couldn't be sent!");
	}
}
