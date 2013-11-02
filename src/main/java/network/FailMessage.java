package network;

import model.Task;

public class FailMessage <S> extends Message <S>{
	private Task task;
	private long elapsedTime;

	public FailMessage(S sender, Task task, long elapsedTime){
		super(sender);
		this.task= task;
		this.elapsedTime = elapsedTime;
	}
	
	public Task getTask(){ return task;}

	public long getElapsedTime(){ return elapsedTime;}
}
