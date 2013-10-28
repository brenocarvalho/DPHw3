package FakeServer;

import java.util.LinkedList;
import java.util.List;

import model.Task;
import Client.IServer;
import Network.*;

public class InstantTimeServer implements IServer{

	private List<Message> in_messages, out_messages;
	private boolean busy;
	
	public InstantTimeServer(){
		busy = false;
		in_messages = new LinkedList<Message>();
		out_messages = new LinkedList<Message>();
	}
	
	public void remove() {
		out_messages.remove(0);		
	}

	// TODO change this raw type to the client type
	public void request(TaskMessage message) {
		Task task = message.getTask();
		if(!busy){
			busy = true;
			Message out = null;
			if(in_messages.isEmpty()){
				try {
					long begin = System.nanoTime();
					task.run();
					//System.out.printf("Elapsed: %d nano sec\n", System.nanoTime()-begin);
					out = new SuccessMessage<IServer>(this, task);
				} catch (Exception e) {
					out = new FailMessage<IServer>(this, task);
				}
				out_messages.add(out);
			}else{
				while(!in_messages.isEmpty()){
					task = ((TaskMessage) in_messages.remove(0)).getTask();
					try {
						task.run();
						out = new SuccessMessage<IServer>(this, task);
					} catch (Exception e) {
						out = new FailMessage<IServer>(this, task);
						continue;
					}
					out_messages.add(out);
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
	public boolean isBusy(){
		return busy;
	}

	public boolean hasNext() {
		return !out_messages.isEmpty();
	}

	public Message next() {
		return out_messages.remove(0);
	}

}
