package tdnext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import tdnext.TDNextLogicAPI.CommandType;

public class TDNext {
	
	private ArrayList<Task> _listTask = new ArrayList<Task>();
	private String _lastCommand = new String();
	private Task _lastTask;

	public TDNext(){
	}
	
	public ArrayList<Task> executeCommand(String input) {
		_lastCommand = input;
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
		CommandType command = ParserAPI.parseCommand(_lastCommand);
		
		switch(command) {
			case ADD :  
				undoAdd();
				
			case DELETE : 
				undoDelete();
			
			case EDIT :
				undoEdit();
				
			case CLEAR :
				undoClear();
			
			case DONE :
				undoMarkAsDone();
		}
	}

	private void undoMarkAsDone() {
		int index = ParserAPI.parseIndex(_lastCommand);
		Task currTask = _listTask.get(index);
		String oldDesc = currTask.getDescription();
		currTask.markAsUndone();
		String newDesc = currTask.getDescription();
		StorageAPI.editToFile(oldDesc, newDesc);
	}

	private void undoClear() {
		// TODO Auto-generated method stub
		
	}

	private void undoEdit() {
		// TODO Auto-generated method stub
		
	}

	private void undoDelete() {
		// TODO Auto-generated method stub
	}

	private void undoAdd() {
		// TODO Auto-generated method stub
	}

	private void editTask(String input) {	
	}

	private void clearAll() throws IOException {
		_listTask.clear();
		StorageAPI.clearFile();		
	}

	private void markTaskAsDone(String input) {
		int index = ParserAPI.parseIndex(input);
		Task currTask = _listTask.get(index);
		String oldDesc = currTask.getDescription();
		currTask.markAsDone();
		String newDesc = currTask.getDescription();
		StorageAPI.editToFile(oldDesc, newDesc);
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
