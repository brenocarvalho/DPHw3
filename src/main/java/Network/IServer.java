package Network;

public interface IServer extends Iterable<Message>{
	public abstract void acceptRequest(TaskMessage message);
	public abstract void getStatus();
	
	public abstract void informSuccess(IClient client, SuccessMessage success);
	public abstract void informFail(IClient client, FailMessage success);
	//public abstract IMessage informClientIsUp(); //informs that the client is online again
	
	public abstract boolean isActive();
	public abstract boolean hasNext();
	public abstract Message next();
}