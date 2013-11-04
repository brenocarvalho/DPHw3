package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import parsing.Parser;

import model.Graph;
import model.LogEntry;
import model.Task;
import network.Constants.SERVER_STATUS;
import network.*;

public class ClientNetInterface implements IServer, Runnable{
	private Socket client;
	private ObjectInput input;
	private ObjectOutput output;
	private ServerMenager menager;
	private List<LogEntry> log;
	private String name;
	private SERVER_STATUS status;
	
	public static void main(String[] args){
		ServerMenager menager;
		ClientNetInterface server = null;
		Scheduler sc;
		Thread listener;
		try{
			if( args.length < 2){
				System.out.println("Usage: java ClientNetInterface [file_name] [hostname]+");
				System.exit(0);
			}
			Parser parser = new Parser();
			parser.parse(args[0]);
			Graph g = parser.getGraph();
			sc = new Scheduler(g);
	
			menager = new ServerMenager(sc);
			for(int i = 1; i< args.length; i++){
				server = new ClientNetInterface(menager, args[i], Constants.DEFAULT_PORT);
				menager.addServer(server);
				listener = new Thread(server);
				listener.start();
			}
			System.out.println("Client ready");
	
			while(menager.hasMessage()){
				try {
					menager.delegate();
					Thread.sleep(Constants.MAX_TIMEOUT*5);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			
			for(LogEntry entry: server.getLog()){
				System.out.println(entry);
			}
		}finally{
			server.closeSocket();
		}
	}
	
	public ClientNetInterface(ServerMenager menager, String name, int port){
		try {
			this.menager = menager;
			this.name = name;
			status = SERVER_STATUS.Avaible; 
			log = new ArrayList();
			boolean connectionFlag = true;
			while(connectionFlag){
				try{
					client = new Socket(name, port);
					connectionFlag = false;	
				} catch(ConnectException e){
					connectionFlag = true;
					Thread.sleep(Constants.LISTEN_INTERVAL);
				}
			}
			System.out.println("Client ready");
			client.getInputStream();
			client.getOutputStream();
			output = new ObjectOutputStream(client.getOutputStream());
			output.flush();
			input = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e){}
	}
	
	public void closeSocket(){
		try {
			input.close();
			output.close();
			client.close();
		} catch (IOException e) {}
		  catch (NullPointerException e) {}

	}
	
	public ServerMenager getMenager(){ return menager;}
	
	public List<LogEntry> getLog(){ return log;}
	
	public void sendRequest(TaskMessage message) {
		try {
			output.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SERVER_STATUS getStatus() {
		return status;
	}

	public void run() {
		//This method keeps listening the socket
		Message message;
		Task task = null;
		long time = -1;
		
		while(menager.hasMessage()){
			try {
				System.out.print("message comming\n");
				message = (Message) input.readObject();
				if(message != null){
					if(message instanceof ServerStatusMessage){
						//TODO server status manipulation
					}else{
						if(message instanceof SuccessMessage){
							SuccessMessage m = ((SuccessMessage)message); 
							task = m.getTask();
							time = m.getElapsedTime();
						}
						if(message instanceof FailMessage){
							FailMessage m = ((FailMessage)message); 
							task = m.getTask();
							time = m.getElapsedTime();
						}
						menager.removeProcessingTask(task);
						log.add(new LogEntry(task, message.getSender(), time));
					}
					status = SERVER_STATUS.Avaible;
				}
				Thread.sleep(Constants.LISTEN_INTERVAL);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				status = SERVER_STATUS.Dead;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String toString(){
		return "Server: "+name;
	}
}
