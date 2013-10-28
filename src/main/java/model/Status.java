package model;

public class Status {
	private String msg;
	public static final Status	SUCCESS = 	new Status("Successfuly executed"),
									FAIL 	=	new Status("The execution Failed"),
									RUNNING =	new Status("Running");
	public Status(String message){
		msg = message;
	}
	
	@Override
	public String toString(){
		return msg;
	}
}
