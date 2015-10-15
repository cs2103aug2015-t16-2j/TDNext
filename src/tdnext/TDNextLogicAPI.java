package tdnext;

import java.util.ArrayList;

public class TDNextLogicAPI {
	
	private Logic _first;
	
	public enum ColourType {
		RED, WHITE, GREEN, YELLOW
	}
	
	public enum CommandType {
		ADD, DONE, DELETE, EDIT, CLEAR, SEARCH, SORT_DEFAULT, SORT_BY_NAME, 
		SORT_BY_DEADLINE, UNDO, EXIT, INVALID
	}
	
	public TDNextLogicAPI(){
		_first = new Logic();
	}
	
	// This method receives a string which is the command.
	// Returns an array of Task objects
	public ArrayList<Task> executeCommand(String input) {
		ArrayList<Task> output = _first.executeCommand(input);
		
		return output;
	}
	
	// This method runs at the start of the program and 
	// returns an array of events
	public ArrayList<Task> startProgram() {
		ArrayList<Task> output = _first.startProgram();
		
		return output;
	}
}
