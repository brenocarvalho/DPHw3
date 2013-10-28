package parsing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Graph;
import model.Node;
import model.Task;

public class ChainTasksCommandTest {

	@Test
	public void testRun() {
		try{
			Graph g1 = new Graph(), g2 = new Graph();
			Node a1 = new Node(new Task("t"+1, 100), g1);
			Node a2 = new Node(new Task("t"+1, 100), g2);
			String command = "t1";
			for(int i = 2; i <  10; i++){
				Node b1 = new Node(new Task("t"+i, 100), g1);
				Node b2 = new Node(new Task("t"+i, 100), g2);
				b1.addParent(a1);
				command += " -> "+b2.getTaskName();
				a1 = b1;
				a2 = b2;
			}
			Command c = new ChainTasksCommand(g2);
			c.run(command);
			assertTrue("Graph not well chained", g1.toString().equals(g2.toString()));
		}catch(Exception e){
			fail();
		}
	}

}
