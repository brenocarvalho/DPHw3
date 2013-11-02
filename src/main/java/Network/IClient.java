package Network;

public interface IClient {
	
	public void requestExecution(TaskMessage taskM);
	
	public void receiveSuccess(IServer server, SuccessMessage success);
	
	public void receiveFailure(IServer server, FailMessage success);
	
	public void receiveStatus(IServer server, ServerStatusMessage status);
}
