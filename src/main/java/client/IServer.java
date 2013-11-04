package client;

import network.Constants;
import network.TaskMessage;

public interface IServer{
	public void sendRequest(TaskMessage message);
	public Constants.SERVER_STATUS getStatus();
}