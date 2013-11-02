package network;

import model.Task;

public class TaskMessage <S> extends Message<S>{
	private Task task;
	private S sender;
		
	public TaskMessage(S sender, Task task){
		super(sender);
		this.task = task;
	}
	
	public Task getTask(){ return task;}
	
	public String toString(){
		return "Task Message: "+task.toString();
	}

	public S getSender() { return sender;}

}
