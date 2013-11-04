package model;

import java.io.Serializable;

public class Status implements Serializable{
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
