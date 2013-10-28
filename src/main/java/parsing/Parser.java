package parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Graph;

public class Parser {
	private Graph graph;
	
	public Parser(){
		graph = new Graph();
	}
	
	public Graph getGraph(){
		return graph;
	}
	
	private void parseScanner(Scanner scanner, Command parser){
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			try {
				parser.run(line);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	public void parse(String file){
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		Command parser = new CreateTaskCommand(graph);
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			try {
				parser.run(line);
			} catch (Exception e) {
				parser = new ChainTasksCommand(graph);
				try {
					parser.run(line);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
}
