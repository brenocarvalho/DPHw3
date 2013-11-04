package fakeNetwork;

import static org.junit.Assert.*;
import model.Task;
import network.*;

import org.junit.Test;

import server.Agent;

import fakeNetwork.RandomClient;;

public class RandomClientTest {
	
	@Test
	public void testA(){
		RandomClient client = new RandomClient("random");
		Agent agent = new Agent("slave", client);
		try {
			client.sendTasks(10);
		} catch (Exception e) {
			fail();
		}
	}

}
