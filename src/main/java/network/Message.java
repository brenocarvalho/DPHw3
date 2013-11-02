package network;

public abstract class Message<S>{
	private S sender;
	public S getSender(){ return sender;}
	
	public Message(S sender){
		this.sender = sender;
	}
}
