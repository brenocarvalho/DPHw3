package Client;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import Network.*;

import model.Task;

public class ServerMenager implements Observer{
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
				server.request(new TaskMessage(this, task));
				return;
			}
		}
		
		if(localServer.isActive()){
			localServer.request(new TaskMessage(this, task));
			return;
		}
		throw new Exception("Message couldn't be sent!");
	}

	public void update(Observable arg0, Object arg1) {
		//TODO This method takes a message from the server and process it to free more tasks.
		/*
		IServer server = (IServer) arg0;
		for(Message msg: server){
			if(msg instanceof SuccessMessage){
				SuccessMessage success = (SuccessMessage) msg;
			
			}else{
				if(msg instanceof FailMessage){
					SuccessMessage success = (SuccessMessage) msg;
				
				}
			}
		}
		*/
	}
}
