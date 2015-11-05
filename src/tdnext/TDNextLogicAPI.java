//@author Adriel
package tdnext;

import java.util.ArrayList;

public class TDNextLogicAPI {

	private Logic _first;

	public enum ColourType {
		RED, WHITE, GREEN, YELLOW
	}

	public enum CommandType {
		ADD, ADD_ALL, CLEAR, CHANGE_DIRECTORY, DONE, DELETE, EDIT, EDIT_DATE,
		SEARCH, SORT_DEFAULT, SORT_BY_NAME, SORT_BY_DEADLINE, UNDO, UNDONE,
		EXIT, INVALID
	}

	public TDNextLogicAPI(){
		_first = new Logic();
	}

	// This method receives a string which is the command.
	// Returns an array of Task objects
	public ArrayList<Task> executeCommand(String input) throws Exception {
		ArrayList<Task> output = _first.executeCommand(input);

		return output;
	}

	// This method runs at the start of the program and
	// returns an array of events
	public ArrayList<Task> startProgram() throws Exception {
		ArrayList<Task> output = _first.startProgram();

		return output;
	}
}
