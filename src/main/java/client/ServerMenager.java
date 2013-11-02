package client;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import network.*;
import network.Constants.SERVER_STATUS;


import model.Task;

public class ServerMenager{
	private List<IServer> servers;
	private IServer localServer;
	
	public ServerMenager(){
		servers = new LinkedList<IServer>();
		localServer = new fakeNetwork.InstantTimeServer("localServer");
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
	
	public void delegate(Task task) throws Exception{
		for(IServer server: servers){
			if(server.getStatus() == SERVER_STATUS.Avaible){
				server.sendRequest(new TaskMessage(this, task));
				return;
			}
		}
		
		if(localServer.getStatus() == SERVER_STATUS.Avaible){
			localServer.sendRequest(new TaskMessage(this, task));
			return;
		}
		throw new Exception("Message couldn't be sent!");
	}
}
