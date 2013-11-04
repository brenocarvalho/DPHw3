package fakeNetwork;

import static org.junit.Assert.*;
import model.Graph;
import model.Node;
import model.Task;
import network.*;

import org.junit.Test;

import client.Scheduler;
import client.ServerMenager;

import fakeNetwork.InstantTimeServer;


public class InstantServerTest {
	private InstantTimeServer server;
	private Task tA;
	private ServerMenager menager;
	
	public InstantServerTest() throws Exception {
		Graph g = new Graph();
		tA = new Task("T1",100);
		Node a = new Node(tA);
		Scheduler sc = new Scheduler(g);
		menager = new ServerMenager(sc);
		server = new InstantTimeServer("localTest", menager);
		menager.addServer(server);
	}
	@Test
	public void testSingleTask(){
		server.sendRequest(new TaskMessage("client", tA));
		assertTrue("Task doesn't eliminated in client", !menager.hasMessage());
	}
	
}
