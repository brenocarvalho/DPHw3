package parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Code;
import model.CountCode;
import model.Graph;
import model.Node;
import model.SleepCode;
import model.Task;

public class ChainTasksCommand implements Command{
	private Graph graph;
	
	public ChainTasksCommand(Graph graph) {
		this.graph = graph;
	}
	
	private Node taskNameToNode(String name) throws Exception{
		Node n = graph.getNodebyTaskName(name);
		if(n == null){
			throw new Exception("Task "+name+"doesn't exists.");
		}
		return n;
	}
	
	public void run(String arg) throws Exception{
		//format: "Task1Name -> Task2Name -> [...]"
		//format: "Task1Name -> (Task
		
		Pattern r1 = Pattern.compile("^\\w+");
		Pattern r2 = Pattern.compile("\\s*->\\s*(\\w+)");
		Matcher m1 = r1.matcher(arg);
		Matcher m2 = r2.matcher(arg);
		Exception creationEx = new Exception("Invalid format while building the tree");
		Node a,b;
		if(m1.find()){
			//System.out.println(m1.group());
			a = taskNameToNode(m1.group());
		}
		else{
			throw creationEx;
		}
		while(m2.find()){
			//System.out.println(m2.group(1));
			b = taskNameToNode(m2.group(1));
			b.addParent(a);
			System.out.println(String.format("%s -> %s",a.getTaskName(), b.getTaskName()));
			a = b;
		}
	}

}
