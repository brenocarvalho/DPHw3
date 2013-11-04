package network;

import model.Task;

public class TaskMessage extends Message{
	private Task task;
	private String sender;
		
	public TaskMessage(String sender, Task task){
		super(sender);
		this.task = task;
	}
	
	public Task getTask(){ return task;}
	
	public String toString(){
		return "Task Message: "+task.toString();
	}

	public String getSender() { return sender;}

}
