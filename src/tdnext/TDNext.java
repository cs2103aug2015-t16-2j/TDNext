package tdnext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
		Collections.sort(_listTask, new NameComparator());
	}

	private void sortDeadline() {
		Collections.sort(_listTask, new DateComparator());
		
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
		}
	}
	
	private void deleteTask(String input) {
		int index = ParserAPI.parseIndex(input);
		_listTask.remove(index);
	}
	
	private ArrayList<Task> searchTask(String input) {
		ArrayList<String> information = ParserAPI.parseInformation(input);
		String name = information.get(0);
		ArrayList<Task> output = new ArrayList<Task>();
		
		for(int i = 0; i < _listTask.size(); i++) {
			Task currTask = _listTask.get(i);
			if(currTask.getDescription().contains(name)) {
				output.add(currTask);
			}
		}
		
		return output;
	}
	
	private void sortDefault() {
		Collections.sort(_listTask, new PriorityComparator());
	}
}
