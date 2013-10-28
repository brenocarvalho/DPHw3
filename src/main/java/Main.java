import model.*;
import Client.*;
import FakeServer.*;
import Network.*;

public class Main {
	public static void main(String args[]) throws Exception{
		
		IServer server = new InstantTimeServer();
		server.request(new TaskMessage<Object>(null, new Task("Hi",1)));
		if(server.hasNext()){
			System.out.print(((SuccessMessage) server.next()).getTask().getName());
		}
		
		/*
		if( args.length < 2){
			throw new Exception("Bad parameters. I need a filename and a threads number.");
		}
		Parser parser = new Parser();
		parser.parse(args[0]);
		Graph g = parser.getGraph();
		Scheduler sc = new Scheduler(Integer.valueOf(args[1]).intValue(), g);
		sc.execute();
		Thread.sleep(1000);
		System.out.println(sc);
		*/
	}
}
