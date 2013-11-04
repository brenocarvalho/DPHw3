package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import network.Constants;
import network.FailMessage;
import network.ServerStatusMessage;
import network.SuccessMessage;
import network.TaskMessage;

public class ServerNetInterface implements IClient, Runnable{
	ServerSocket server;
	Socket service;
	ObjectInput input;
	ObjectOutput output;
	Agent agent;
	List<TaskMessage> toRun;
	
	public static void main(String[] args) throws Exception{
		
		ServerNetInterface client = new ServerNetInterface();
		Agent agent = new Agent("local", client);
		Thread listener = new Thread(client);
		listener.start();
		
		while(true){
			if(!client.toRun.isEmpty()){
				agent.execute(client.toRun.remove(0));
			}
			Thread.sleep(Constants.LISTEN_INTERVAL);
		}
	}
	
	public ServerNetInterface(){
		try {
			server = new ServerSocket(1510);
			System.out.println("Server ready");
			service = server.accept();
			System.out.println("Server ready!");
			output = new ObjectOutputStream(service.getOutputStream());
			output.flush();
			input = new ObjectInputStream(service.getInputStream());
			
			System.out.println("Server ready!!");
			toRun = new ArrayList<TaskMessage>();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeSocket(){
		try {
			input.close();
			output.close();
			service.close();
			server.close();
		} catch (IOException e) {}

	}
	
	public void receiveSuccess(SuccessMessage success) {
		try {
			output.writeObject(success);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void receiveFailure(FailMessage fail) {
		try {
			output.writeObject(fail);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void receiveStatus(ServerStatusMessage status) {
		try {
			output.writeObject(status);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public void run() {
		//This method keeps listening the socket
		TaskMessage message;
		while(true){
			try {
				if(input.available()>0){
					message = (TaskMessage) input.readObject();
					if(message != null){
						toRun.add(message);
					}
					Thread.sleep(Constants.LISTEN_INTERVAL);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
