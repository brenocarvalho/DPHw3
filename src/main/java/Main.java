import model.*;
import parsing.Parser;

public class Main {
	public static void main(String args[]) throws Exception{
		if( args.length < 2){
			throw new Exception("Bad parameters. I need a filename and a threads number.");
		}
		Parser parser = new Parser();
		parser.parse(args[0]);
		Graph g = parser.getGraph();
		/*
		Graph g = new Graph();
		Command t = new CreateTaskCommand(g);
		int wait = 500;
		t.run("t1 := sleep "+wait);
		t.run("t2 := sleep "+wait);
		t.run("t3 := sleep "+wait);
		t.run("t4 := sleep "+wait);
		t.run("t5 := sleep "+wait);
		Command u = new ChainTasksCommand(g);
		u.run("t1 -> t2 -> t3 -> t5");
		u.run("t2 -> t4 -> t5");
		System.out.println(g.toString());
		
		/*
		Node p,q, r = null;
		
		p = new Node(new Task("T1", wait), g);
		for(int i = 2; i < 7; i++){
			q = new Node(new Task("T"+i, wait), g);
			if( i == 3){
				r = q;
			}
			q.addParent(p);
			//System.out.print("["+q.toString()+" " +g.contains(q)+"]\n");
			p = q;
		}
		q = new Node(new Task("T5.5", wait), g);
		p.addParent(q); // T5.5 must run first than T6
		q.addParent(r); // T3 must run first than T5.5
		//JOptionPane.showMessageDialog(null, g.contains(q)+" "+q.getNumParents()+" "+r.getTaskName()+" "+r.getNumChildren());
		*/
		//System.out.println(g);
		
		
		Scheduler sc = new Scheduler(Integer.valueOf(args[1]).intValue(), g);
		sc.execute();
		//System.out.println("Orphan tasks: "+g.hasOrphan());
		Thread.sleep(1000);
		System.out.println(sc);
		//JOptionPane.showMessageDialog(null, g.contains(q)+" "+q.getNumParents()+" "+r.getTaskName()+" "+r.getNumChildren());
	}
}
