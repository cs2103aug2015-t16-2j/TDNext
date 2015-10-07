package tdnext;

import java.util.ArrayList;

public class TDNextLogicAPI {
	
	private TDNext _first;
	
	public enum ColourType {
		RED, WHITE, GREEN, YELLOW
	}
	
	public enum CommandType {
		ADD, DONE, DELETE, EDIT, CLEAR, SEARCH, SORT_DEFAULT, SORT_BY_NAME, 
		SORT_BY_DEADLINE, UNDO, EXIT, INVALID
	}
	
	public TDNextLogicAPI(){
		_first = new TDNext();
	}
	
	// This method receives a string which is the command.
	// Returns an array of Task objects
	public ArrayList<Task> executeCommand(String input) {
		ArrayList<Task> output = _first.executeCommand(input);
		
		/*ArrayList<Task> output = new ArrayList<Task>();
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("IMPORTANT and 1 DAY TO DEADLINE");
		list.add("IMPORTANT");
		list.add("28/09/15");
		output.add(new Task(list));
		list.clear();
		list.add("Not Important and 1 DAY TO DEADLINE");
		list.add("");
		list.add("28/09");
		output.add(new Task(list));
		list.clear();
		list.add("Not Important and 14 DAYS TO DEADLINE");
		list.add("");
		list.add("11/10/15");
		output.add(new Task(list));
		list.clear();
		list.add("IMPORTANT and NO DEADLINE");
		list.add("IMPORTANT");
		list.add("");
		output.add(new Task(list));
		list.clear();
		list.add("IMPORTANT and 15 DAYS TO DEADLINE");
		list.add("IMPORTANT");
		list.add("12/10/15");
		output.add(new Task(list));
		list.clear();
		list.add("Not Important and NO DEADLINE");
		list.add("");
		list.add("");
		output.add(new Task(list));*/
		
		return output;
	}
	
	// This method runs at the start of the program and 
	// returns an array of events
	public ArrayList<Task> startProgram() {
		ArrayList<Task> output = _first.startProgram();
		
		/*ArrayList<Task> output = new ArrayList<Task>();
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("IMPORTANT and 1 DAY TO DEADLINE");
		list.add("IMPORTANT");
		list.add("28/09/15");
		output.add(new Task(list));
		list.clear();
		list.add("Not Important and 1 DAY TO DEADLINE");
		list.add("");
		list.add("28/09");
		output.add(new Task(list));
		list.clear();
		list.add("Not Important and 14 DAYS TO DEADLINE");
		list.add("");
		list.add("11/10/15");
		output.add(new Task(list));
		list.clear();
		list.add("IMPORTANT and NO DEADLINE");
		list.add("IMPORTANT");
		list.add("");
		output.add(new Task(list));
		list.clear();
		list.add("IMPORTANT and 15 DAYS TO DEADLINE");
		list.add("IMPORTANT");
		list.add("12/10/15");
		output.add(new Task(list));
		list.clear();
		list.add("Not Important and NO DEADLINE");
		list.add("");
		list.add("");
		output.add(new Task(list));*/
		
		return output;
	}
}
