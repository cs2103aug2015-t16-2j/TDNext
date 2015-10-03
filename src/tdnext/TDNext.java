package tdnext;

import java.io.IOException;
import java.util.ArrayList;

import tdnext.TDNextLogicAPI.CommandType;

public class TDNext {
	
	private ArrayList<Task> _listTask = new ArrayList<Task>();
	
	public TDNext(){
	}
	
	public ArrayList<Task> executeCommand(String input) {
		CommandType command = ParserAPI.parseCommand(input);
		
		switch (command) {
			case ADD :  
				addTask(input);
				sortDefault();
				return _listTask;
				
			case DELETE : 
				deleteTask(input);
				sortDefault();
				return _listTask;
			
			case SEARCH :
				ArrayList<Task> output = searchTask(input);
				return output;
			
			case EDIT :
				editTask(input);
				sortDefault();
				return _listTask;
				
			case CLEAR :
				clearAll();
				return _listTask;
			
			case DONE :
				markTaskAsDone(input);
				return _listTask;
				
			case SORT_DEFAULT :
				sortDefault();
				return _listTask;
			
			case SORT_BY_NAME :
				sortName();
				return _listTask;
				
			case SORT_BY_DEADLINE :
				sortDeadline();
				return _listTask;
				
			case UNDO:
				undo();
				return _listTask;
				
			case EXIT :
				exitProgram();
				return _listTask;
			
			default :
				return _listTask;
		}
	}
	


	private void undo() {
		// TODO Auto-generated method stub
		
	}

	private void editTask(String input) {
		// TODO Auto-generated method stub
		
	}

	private void clearAll() {
		// TODO Auto-generated method stub
		
	}

	private void markTaskAsDone(String input) {
		// TODO Auto-generated method stub
		
	}

	private void sortName() {
		// TODO Auto-generated method stub
		
	}

	private void sortDeadline() {
		// TODO Auto-generated method stub
		
	}

	private void exitProgram() {
		// TODO Auto-generated method stub
		
	}

	private void addTask(String input) {
		ArrayList<String> information = ParserAPI.parseInformation(input);
		Task newTask = new Task(information);
		_listTask.add(newTask);
		try {
			StorageAPI.writeToFile(newTask.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void deleteTask(String input) {
	}
	
	private ArrayList<Task> searchTask(String name) {
		return new ArrayList<Task>(); 
	}
	
	private void sortDefault() {
		// TODO Auto-generated method stub
		
	}
}
