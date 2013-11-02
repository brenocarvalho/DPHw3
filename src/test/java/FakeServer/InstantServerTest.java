package FakeServer;

import static org.junit.Assert.*;
import model.Task;
import network.*;

import org.junit.Test;

import fakeNetwork.InstantTimeServer;


public class InstantServerTest {
	private InstantTimeServer server;
	
	public InstantServerTest() {
		server = new InstantTimeServer("localTest");
	}
	@Test
	public void testSingleTask(){
		Task t = null;
		try {
			t = new Task("Task1", 100);
		} catch (Exception e) {
			fail("Couldn't create the task!");
		}
		assertTrue("t != null", t != null);
		server.sendRequest(new TaskMessage<Object>(this, t));
		try {
			Thread.sleep(Constants.MAX_TIMEOUT);
		} catch (InterruptedException e) {}
		assertTrue("Expected task didn't run", server.getSuccessIterator().next().getTask().equals(t));
	}
	
}
