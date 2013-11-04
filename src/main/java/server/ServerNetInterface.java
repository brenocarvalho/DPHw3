package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
				//JOptionPane.showConfirmDialog(null,"Server received");
			}
			Thread.sleep(Constants.LISTEN_INTERVAL);
		}
	}
	
	public ServerNetInterface(){
		try {	
			server = new ServerSocket(1510);
			service = server.accept();
			output = new ObjectOutputStream(service.getOutputStream());
			output.flush();
			input = new ObjectInputStream(service.getInputStream());
			System.out.println("Server ready!!");
			toRun = new ArrayList<TaskMessage>();
		} catch (Exception e) {
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
	
//	public void setBusy(boolean val){ busy = val;}
	
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
		
		try {
			while(true){
				message = (TaskMessage) input.readObject();
				System.out.println("Message received");
				if(message != null){
					toRun.add(message);
				}
				Thread.sleep(Constants.LISTEN_INTERVAL);
			}
		} catch (EOFException e) {
			System.out.print("Connection Closed");
		} catch (InterruptedException e) {
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
	}	
}
