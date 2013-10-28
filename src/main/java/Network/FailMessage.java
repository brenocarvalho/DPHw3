package Network;

import model.Task;

public class FailMessage <S> extends Message <S>{
	Task task;

	public FailMessage(S sender, Task task){
		super(sender);
		this.task= task;
	}
	
	public Task getTask(){ return task;}

}
