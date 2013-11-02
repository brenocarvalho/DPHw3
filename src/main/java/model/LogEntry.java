package model;

import Network.IServer;

public class LogEntry {
	private Task task;
	private IServer server;
	private long timeElapsed;
	
	public LogEntry(Task task, IServer server, long timeElapsed){
		this.task = task;
		this.server = server;
		this.timeElapsed = timeElapsed;
	}

	public Task getTask() {
		return task;
	}

	public IServer getServer() {
		return server;
	}

	public long getTimeElapsed() {
		return timeElapsed;
	}
	
	public String toString(){
		return String.format("Task:%10s Server:%10s elapsed:%d %s", task, server, timeElapsed, task.getStatus());
	}
}
