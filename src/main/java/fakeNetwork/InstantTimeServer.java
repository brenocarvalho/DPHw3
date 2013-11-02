package fakeNetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import network.Constants;

import server.IClient;

import client.IServer;

import network.*;
import network.Constants.SERVER_STATUS;

import model.Task;

public class InstantTimeServer implements IServer{
	private String name;
	private boolean busy;
	private List<SuccessMessage> successes;
	private List<FailMessage> fails;
	private long lastMessageSentTime;
	
	public InstantTimeServer(String name){
		this.name = name;
		busy = false;
		successes = new ArrayList<SuccessMessage>();
		fails = new ArrayList<FailMessage>();
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
		if(!fail){
			successes.add(new SuccessMessage<String>(name, task, end-start));
			return;
		}
		fails.add(new FailMessage<String>(name, task, end-start));
	}

	public boolean isBusy(){ return busy;}
	
	public SERVER_STATUS getStatus() {
		if(Math.abs(System.currentTimeMillis()-lastMessageSentTime) > Constants.MAX_TIMEOUT){
			return SERVER_STATUS.Dead;
		}
		if(!isBusy()){
			return SERVER_STATUS.Avaible;
		}
		return SERVER_STATUS.Busy;
	}

	public Iterator<SuccessMessage> getSuccessIterator() {
		return successes.iterator();
	}

	public Iterator<FailMessage> getFailIterator() {
		return fails.iterator();
	}
}
