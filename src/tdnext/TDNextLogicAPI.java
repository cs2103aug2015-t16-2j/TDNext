package tdnext;

import java.util.ArrayList;

//@author A0125283J
public class TDNextLogicAPI {

	private Logic _first;

	public enum ColourType {
		RED, WHITE, GREEN, YELLOW
	}

	public enum CommandType {
		ADD, ADD_ALL, CLEAR, CHANGE_DIRECTORY, DONE, DELETE, EDIT, EDIT_DATE,
		SEARCH, SEARCH_DATE, SEARCH_TIME, SORT_DEFAULT, SORT_BY_NAME,
		SORT_BY_DEADLINE, UNDO, UNDONE, EXIT, INVALID
	}

	public TDNextLogicAPI(){
		_first = new Logic();
	}

	// This method receives a string which is the command.
	// Returns an array of Task objects
	public ArrayList<Task> executeCommand(String input) throws TDNextException {
		ArrayList<Task> output = _first.executeCommand(input);

		return output;
	}

	// This method runs at the start of the program and
	// returns an array of events
	public ArrayList<Task> startProgram() throws TDNextException {
		ArrayList<Task> output = _first.startProgram();

		return output;
	}

	public void setTheme(String theme) throws TDNextException {
		return _first.setTheme(theme);
	}

	public String getTheme() throws TDNextException {
		return _first.getTheme();
	}
}
