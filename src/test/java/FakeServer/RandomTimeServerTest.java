package FakeServer;

import static org.junit.Assert.*;
import model.Task;

import org.junit.Test;

import Network.*;

public class RandomTimeServerTest {
	private RandomTimeServer server;
	
	public RandomTimeServerTest() {
		server = new RandomTimeServer();
	}
	@Test
	public void test1(){
		Task t = null;
		try {
			t = new Task("Task1", 100);
		} catch (Exception e) {
			fail("Couldn't create the task!");
		}
		assertTrue("t != null", t != null);
		server.request(new TaskMessage<Object>(this, t));
		assertTrue("Expected task didn't run",((SuccessMessage) server.next()).getTask().equals(t));
	}
	
}
