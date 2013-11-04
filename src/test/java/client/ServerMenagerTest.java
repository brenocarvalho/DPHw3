package client;

import static org.junit.Assert.*;

import model.Graph;
import model.Node;
import model.SleepCode;
import model.Task;

import org.junit.Test;

import parsing.Parser;
import fakeNetwork.InstantTimeServer;

public class ServerMenagerTest {
	
	@Test
	public void testGetFreeServer() throws Exception{
		Graph g = new Graph();
		IServer a, free;
		
		Scheduler sc = new Scheduler(g);
		ServerMenager menager = new ServerMenager(sc);
		a = new InstantTimeServer("a", menager);
		menager.addServer(a);
		free = menager.getFreeServer();
		assertTrue("Wrong server scheduled", free == a);
		menager.removeServer(a);
		free = menager.getFreeServer();
		assertTrue("Local server cannot be activated", free != null);
	}
	
	@Test
	public void testRemoveProcessingTask() throws Exception {
		Graph g = new Graph();
		Node a = new Node(new Task("TaskA",100),g);
		Node b = new Node(new Task("TaskB",100),g);
		b.addParent(a);
		Scheduler sc = new Scheduler(g);
		ServerMenager menager = new ServerMenager(sc);
		assertTrue("Don't updating scheduler", menager.hasMessage());
		menager.addServer(new InstantTimeServer(String.format("server %d <%s>", 1, "00"),menager));
		menager.loadTasks();
		menager.removeProcessingTask(a.getTask());
		menager.loadTasks();
		menager.removeProcessingTask(b.getTask());
		assertTrue("Don't removing from scheduler", !menager.hasMessage());
	}
	
	@Test
	public void testDelegate() throws Exception {
		Graph g = new Graph();
		Node a = new Node(new Task("TaskA",100),g);
		Node b = new Node(new Task("TaskB",100),g);
		Node c = new Node(new Task("TaskC",100),g);
		b.addParent(a);
		c.addParent(b);
		Scheduler sc = new Scheduler(g);
		ServerMenager menager = new ServerMenager(sc);
		
		menager.addServer(new InstantTimeServer(String.format("server %d <%s>", 1, "00"),menager));
		System.out.print("starting execution");

		while(menager.hasMessage()){
			menager.delegate();
		}
		System.out.print("Done");
	}

}
