package parsing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Graph;
import model.Node;
import model.Task;

public class CreateTaskCommandTest {
	@Test
	public void testRun() {
		try{
			Graph g1 = new Graph();
			Graph g2 = new Graph();
			Command c = new CreateTaskCommand(g2);
			for(int i = 0; i < 5; i++){
				new Node(new Task("t"+i, 100), g1);
				c.run("t"+i+":= sleep 100");
				assertTrue("Node not created", g2.getNumNodes() == g1.getNumNodes());
			}
			assertTrue("Graphs doesnt match!", g1.toString().equals(g2.toString()));
		}catch (Exception e){
			fail("I couldn't create the nodes!");
		}
	}

}
