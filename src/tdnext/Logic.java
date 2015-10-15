package tdnext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import tdnext.ParserAPI;
import tdnext.StorageAPI;
import tdnext.Task;
import tdnext.TDNextLogicAPI.CommandType;

public class Logic {
	
	private ArrayList<Task> _listTask = new ArrayList<Task>();
	private String _lastCommand = new String();
	private ArrayList<Task> _tempTask;
	private static Logger _logger = Logger.getLogger("Logic");

	public Logic(){
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
	
	public ArrayList<Task> startProgram() {
		ArrayList<String> allFileInfo = new ArrayList<String>();
		try {
			allFileInfo = StorageAPI.getFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < allFileInfo.size(); i++) {
			ArrayList<String> information = ParserAPI.parseInformation(allFileInfo.get(i));
			_listTask.add(new Task(information));
		}
		
		return _listTask;
	}
	
	private void undo(){ 
		executeCommand(_lastCommand);
	}

	private void undoMarkAsDone() {
		int index = ParserAPI.parseIndex(_lastCommand);
		Task currTask = _listTask.get(index);
		String oldDesc = currTask.getDescription();
		currTask.markAsUndone();
		String newDesc = currTask.getDescription();
		try {
			StorageAPI.editToFile(oldDesc, newDesc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void editTask(String input) {
		int index = ParserAPI.parseIndex(input);
		String oldDesc = _listTask.get(index).getDescription();
		_listTask.remove(index);
		ArrayList<String> information = ParserAPI.parseInformation(input);
		Task newTask = new Task(information);
		_listTask.add(newTask);
		try {
			StorageAPI.editToFile(newTask.getDescription(), oldDesc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sortDefault();
		_lastCommand = new String();
		_lastCommand = _lastCommand + "EDIT " + _listTask.indexOf(newTask) +
						" " + oldDesc;
	}

	private void clearAll(){
		_tempTask = new ArrayList<Task>(_listTask);
		_listTask.clear();
		try {
			StorageAPI.clearFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private void markTaskAsDone(String input) {
		int index = ParserAPI.parseIndex(input);
		Task currTask = _listTask.get(index);
		String oldDesc = currTask.getDescription();
		currTask.markAsDone();
		String newDesc = currTask.getDescription();
		try {
			StorageAPI.editToFile(oldDesc, newDesc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void exitProgram() {
		// TODO Auto-generated method stub
		
	}

	private void addTask(String input) {
		ArrayList<String> information = ParserAPI.parseInformation(input);
		Task newTask = new Task(information);
		try {
			StorageAPI.writeToFile(newTask.toString());
		} catch (IOException e) {
		}
		_listTask.add(newTask);
		sortDefault();
		int index = _listTask.indexOf(newTask);
		_lastCommand = new String();
		_lastCommand = _lastCommand + "DELETE " + index;
		_logger.log(Level.INFO, "Task added");
		
	}
	
	private void deleteTask(String input) {
		int index = ParserAPI.parseIndex(input);
		Task deletedTask = _listTask.remove(index);
		try {
			StorageAPI.deleteFromFile(deletedTask.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_lastCommand = new String();
		_lastCommand = _lastCommand + "ADD " + deletedTask.toString();
		_logger.log(Level.INFO, "Task deleted");
		
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
		_logger.log(Level.INFO, "Default sorted");
	}
	
	private void sortName() {
		Collections.sort(_listTask, new NameComparator());
		_logger.log(Level.INFO, "Sorted by name");
	}

	private void sortDeadline() {
		Collections.sort(_listTask, new DateComparator());
		_logger.log(Level.INFO, "Sorted by deadline");
		
	}
}
