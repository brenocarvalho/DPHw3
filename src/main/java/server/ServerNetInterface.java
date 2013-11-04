package server;

import java.io.*;
import java.net.Inet4Address;
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
	private ServerSocket server;
	private Socket service;
	private ObjectInput input;
	private ObjectOutput output;
	private Agent agent;
	private int port;
	private List<TaskMessage> toRun;
	
	public static String getAddress(){
		try {
			return Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "localhost";
		}
	}
	
	public static void main(String[] args) throws Exception{
		
		ServerNetInterface client = new ServerNetInterface(Constants.DEFAULT_PORT);
		Agent agent = new Agent(getAddress(), client);
		Thread listener = new Thread(client);
		listener.start();
		
		while(true){
			if(!client.toRun.isEmpty()){
				agent.execute(client.toRun.remove(0));
			}
			Thread.sleep(Constants.LISTEN_INTERVAL);
		}
	}
	
	public void initialize() throws IOException{
		server = new ServerSocket(port);
		System.out.println("Server ready.");
		service = server.accept();
		output = new ObjectOutputStream(service.getOutputStream());
		output.flush();
		input = new ObjectInputStream(service.getInputStream());
	}
	
	public ServerNetInterface(int port){
		try {	
			this.port = port;
			initialize();
			System.out.println("Connection established!");
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
			System.out.print("Connection Closed\n");
			//System.exit(0);
			try {
				closeSocket();
				initialize();
				run();
			} catch (IOException e1) {}
		} catch (InterruptedException e) {
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {}
	}	
}
