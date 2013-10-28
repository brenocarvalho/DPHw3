package FakeServer;

import java.util.List;

import model.Task;
import Client.IServer;
import Network.*;

public class RandomTimeServer implements IServer{

	private List<Message> in_messages, out_messages;
	private boolean busy;
	
	public RandomTimeServer(){
		busy = false;
	}
	
	public void remove() {
		out_messages.remove(0);		
	}

	// TODO change this raw type to the client type
	public void request(TaskMessage message) {
		Task task = message.getTask();
		if(!busy){
			busy = true;
			try {
				task.run();
			} catch (Exception e) {
				// TODO create error message
				out_messages.add(new FailMessage<IServer>(this, task));
			}
			busy = false;
		}
		
	}

	public void getStatus() {
		// TODO implement method to inform if server is online
		
	}

	public boolean hasNext() {
		return out_messages.isEmpty();
	}

	public Message next() {
		return out_messages.remove(0);
	}

}
