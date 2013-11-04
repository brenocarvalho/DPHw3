package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;

import parsing.Parser;

import model.Graph;
import network.Constants.SERVER_STATUS;
import network.*;

public class ClientNetInterface implements IServer, Runnable{
	private Socket client;
	private ObjectInput input;
	private ObjectOutput output;
	private ServerMenager menager;
	
	public static void main(String[] args) throws Exception{
		ServerMenager menager;
		ClientNetInterface server = null;
		Scheduler sc;
		Thread listener;
		try{
			if( args.length < 2){
				//throw new Exception("Usage: java ClientNetInterface [file_name] [hostname]+");
			}
			Parser parser = new Parser();
			parser.parse("file1.txt");//args[0]);
			Graph g = parser.getGraph();
			sc = new Scheduler(g);
	
			menager = new ServerMenager(sc);
			server = new ClientNetInterface(menager);
			menager.addServer(server);
			listener = new Thread(server);
			listener.start();
			System.out.println("Client ready");
	
			while(menager.hasMessage()){
				menager.delegate();
				Thread.sleep(1000);
			}
		}finally{
			server.closeSocket();
		}
	}
	
	public ClientNetInterface(ServerMenager menager){
		try {
			this.menager = menager;
			client = new Socket("localhost", 1510);
			System.out.println("Client ready");
			client.getInputStream();
			client.getOutputStream();
			output = new ObjectOutputStream(client.getOutputStream());
			output.flush();
			input = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeSocket(){
		try {
			input.close();
			output.close();
			client.close();
		} catch (IOException e) {}

	}
	
	public ServerMenager getMenager(){ return menager;}
	
	public void sendRequest(TaskMessage message) {
		try {
			output.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SERVER_STATUS getStatus() {
		return SERVER_STATUS.Avaible;
	}

	public Iterator<SuccessMessage> getSuccessIterator() {
		return null;
	}

	public Iterator<FailMessage> getFailIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void run() {
		//This method keeps listening the socket
		Message message;
		while(menager.hasMessage()){
			try {
				System.out.print("message comming\n");
				message = (Message) input.readObject();
				if(message != null){
					if(message instanceof SuccessMessage){
						menager.removeProcessingTask(((SuccessMessage)message).getTask());
						System.out.print("message processed\n");
					}
					if(message instanceof FailMessage){
						menager.removeProcessingTask(((FailMessage)message).getTask());
						System.out.print("message processed\n");
					}
					if(message instanceof ServerStatusMessage){
						//TODO server status manipulation
					}
				}
				Thread.sleep(Constants.LISTEN_INTERVAL);
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
