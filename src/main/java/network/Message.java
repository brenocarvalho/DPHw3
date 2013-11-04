package network;

public abstract class Message{
	private String sender;
	
	public String getSender(){ return sender;}
	
	public Message(String sender){
		this.sender = sender;
	}
}
