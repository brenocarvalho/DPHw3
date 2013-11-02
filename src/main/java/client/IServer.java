package client;

import java.util.Iterator;

import server.IClient;
import network.Constants;
import network.FailMessage;
import network.Message;
import network.SuccessMessage;
import network.TaskMessage;

public interface IServer{
	public void sendRequest(TaskMessage message);
	public Constants.SERVER_STATUS getStatus();
	
	public Iterator<SuccessMessage> getSuccessIterator();
	public Iterator<FailMessage> getFailIterator();
}