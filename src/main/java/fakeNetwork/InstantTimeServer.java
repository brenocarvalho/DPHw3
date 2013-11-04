package fakeNetwork;

import java.util.ArrayList;
import java.util.Iterator;

import client.IServer;
import client.ServerMenager;

import network.*;
import network.Constants.SERVER_STATUS;

import model.Task;

public class InstantTimeServer implements IServer{
	private String name;
	private ServerMenager menager;
	private long lastMessageSentTime;
	
	public InstantTimeServer(String name, ServerMenager menager){
		this.name = name;
		this.menager = menager;
	}
	
	public void sendRequest(TaskMessage message){
		boolean fail = false;
		Task task = message.getTask();
		long start = System.currentTimeMillis();
		try {
			task.run();
		} catch (Exception e) {
			fail = true;
		}
		long end = System.currentTimeMillis();
		System.out.print(task);
		menager.removeProcessingTask(task);
	}
	
	public SERVER_STATUS getStatus() {
		return SERVER_STATUS.Avaible;
	}

	public Iterator<SuccessMessage> getSuccessIterator() {
		return new ArrayList().iterator();
	}

	public Iterator<FailMessage> getFailIterator() {
		return new ArrayList().iterator();
	}
}
