package server;

import network.FailMessage;
import network.ServerStatusMessage;
import network.SuccessMessage;
import network.TaskMessage;
import client.IServer;

public interface IClient {
	
	public void requestExecution(TaskMessage taskM);
	
	public void receiveSuccess(IServer server, SuccessMessage success);
	
	public void receiveFailure(IServer server, FailMessage fail);
	
	public void receiveStatus(IServer server, ServerStatusMessage status);
}
