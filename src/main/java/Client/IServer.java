package Client;

import Network.*;

public interface IServer extends Iterable<Message>{
	public abstract void request(TaskMessage message);
	public abstract void getStatus();
	//public abstract IMessage informClientIsUp(); //informs that the client is online again
	
	public abstract boolean isActive();
	public abstract boolean hasNext();
	public abstract Message next();
}
