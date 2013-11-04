package client;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;

import parsing.Parser;
import server.Agent;

import model.Graph;
import network.Constants.SERVER_STATUS;
import network.FailMessage;
import network.SuccessMessage;
import network.TaskMessage;

public class ClientNetInterface implements IServer, Runnable{
	Socket client;
	ObjectInput input;
	ObjectOutput output;
	String[] message;
	
	
	
	public static void main(String[] args) throws Exception{
		System.out.println("0Client ready");

		ClientNetInterface server = new ClientNetInterface();
		System.out.println("Client ready");

		if( args.length < 2){
			//throw new Exception(String.format("Usage: java %s [file_name] [hostname]+", args[0]));
		}
		Parser parser = new Parser();
		parser.parse("file1.txt");//args[0]);
		Graph g = parser.getGraph();
		Scheduler sc = new Scheduler(g);
		System.out.println("1Client ready");

		ServerMenager menager = new ServerMenager(sc);
		
		menager.addServer(server);
		System.out.println("2Client ready");
		while(menager.hasMessage()){
			menager.delegate();
		}
	}
	
	public ClientNetInterface(){
		try {

			client = new Socket("localhost", 1510);
			System.out.println("Client ready");

			client.getInputStream();
			client.getOutputStream();
						System.out.println("Client ready++");
			output = new ObjectOutputStream(client.getOutputStream());
			output.flush();
			input = new ObjectInputStream(client.getInputStream());
			System.out.println("Client ready--");

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
	
	public void sendRequest(TaskMessage message) {
		try {
			output.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SERVER_STATUS getStatus() {
		return null;
	}

	public Iterator<SuccessMessage> getSuccessIterator() {
		return null;
	}

	public Iterator<FailMessage> getFailIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

}
