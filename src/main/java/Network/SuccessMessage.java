package Network;

import model.Task;

public class SuccessMessage<S> extends Message <S>{
	private S sender;
	private Task task;
	
	public SuccessMessage(S sender, Task task){
		super(sender);
		this.sender = sender; this.task = task;
	}
	
	public Task getTask(){return task; }
}
