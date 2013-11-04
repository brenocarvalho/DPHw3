package server;

import network.*;

public interface IClient {
	
	public void receiveSuccess(SuccessMessage success);
	
	public void receiveFailure(FailMessage fail);
	
	public void receiveStatus(ServerStatusMessage status);

	public void setAgent(Agent agent);
}
