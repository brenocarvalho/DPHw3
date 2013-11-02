package FakeServer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import model.Task;
import Network.*;

public class InstantTimeServer implements IServer, Iterator{

	private List<Message> in_messages, out_messages;
	private boolean busy;
	private long lastMessageSentTime;
	
	public InstantTimeServer(){
		busy = false;
		in_messages = new LinkedList<Message>();
		out_messages = new LinkedList<Message>();
	}
	
	public void enqueueOutput(Message message){
		out_messages.add(message);
	}
	
	public void remove(){
		out_messages.remove(0);		
	}

	public void acceptRequest(TaskMessage message){
		Task task = message.getTask();
		long timeElapsed = 0;
		Message out = null;
		if(!busy){
			busy = true;			
			if(in_messages.isEmpty()){
				try {
					timeElapsed = System.currentTimeMillis();
					task.run();
					timeElapsed = System.currentTimeMillis() - timeElapsed; 
					//System.out.printf("Elapsed: %d nano sec\n", System.nanoTime()-begin);
					out = new SuccessMessage<IServer>(this, task, timeElapsed);
				} catch (Exception e) {
					busy = false;
					out = new FailMessage<IServer>(this, task, System.currentTimeMillis() - timeElapsed);
				}
				enqueueOutput(out);
			}else{
				while(!in_messages.isEmpty()){
					task = ((TaskMessage) in_messages.remove(0)).getTask();
					try {
						timeElapsed = System.currentTimeMillis();
						task.run();
						timeElapsed = System.currentTimeMillis() - timeElapsed;
						out = new SuccessMessage<IServer>(this, task, timeElapsed);
					} catch (Exception e) {
						out = new FailMessage<IServer>(this, task, System.currentTimeMillis() - timeElapsed);
						continue;
					}
					enqueueOutput(out);
				}
			}
			busy = false;
		}else{
			in_messages.add(message);
		}
		
	}

	public void getStatus(){
		enqueueOutput(new ServerStatusMessage<IServer>(this, isBusy()?0:1));
	}
	
	public boolean isBusy(){
		return busy;
	}

	public boolean isActive(){
		return ((lastMessageSentTime < Constants.MAX_TIMEOUT) || hasNext());
	}
	
	public boolean hasNext(){
		return !out_messages.isEmpty();
	}

	public Message next(){
		return out_messages.remove(0);
	}

	public Iterator iterator(){
		return this;
	}

	public void informSuccess(IClient client, SuccessMessage success){
		client.receiveSuccess(this, success);
	}

	public void informFail(IClient client, FailMessage fail){
		client.receiveFailure(this, fail);
	}

}
