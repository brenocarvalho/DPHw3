package FakeServer;

import java.util.LinkedList;
import java.util.List;

import model.Task;
import Client.IServer;
import Network.*;

public class RandomTimeServer implements IServer{

	private List<Message> in_messages, out_messages;
	private boolean busy;
	
	public RandomTimeServer(){
		busy = false;
		in_messages = new LinkedList();
		out_messages = new LinkedList();
	}
	
	public void remove() {
		out_messages.remove(0);		
	}

	// TODO change this raw type to the client type, and put randomness
	public void request(TaskMessage message) {
		Task task = message.getTask();
		if(!busy || in_messages.isEmpty()){
			busy = true;
			if(in_messages.isEmpty()){
				try {
					task.run();
				} catch (Exception e) {
					// TODO create error message
					out_messages.add(new FailMessage<IServer>(this, task));
				}
				out_messages.add(new SuccessMessage<IServer>(this, task));
			}else{
				while(!in_messages.isEmpty()){
					task = ((TaskMessage) in_messages.remove(0)).getTask();
					try {
						task.run();
					} catch (Exception e) {
						out_messages.add(new FailMessage<IServer>(this, task));
						continue;
					}
					out_messages.add(new SuccessMessage<IServer>(this, task));
				}
			}
			busy = false;
		}else{
			in_messages.add(message);
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
