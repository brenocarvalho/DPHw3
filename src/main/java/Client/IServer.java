package Client;

import java.util.Iterator;

import Network.*;

public interface IServer extends Iterator{
	public abstract void request(TaskMessage message);
	public abstract void getStatus();
	//public abstract IMessage informState(); //informs the client is up again
	
	public abstract boolean hasNext();
	public abstract Message next();
}
