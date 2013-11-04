package network;

import java.io.Serializable;

public abstract class Message implements Serializable{
	private String sender;
	private static int message_counter = 0;
	private int number;
	
	public int hashCode(){return number;}
	
	public String getSender(){ return sender;}
	
	public Message(String sender){
		this.sender = sender;
		number = message_counter++;
	}
}
