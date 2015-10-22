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
	private ArrayList<Task> _searchList;
	public static Logger _logger = Logger.getLogger("Logic");

	public Logic(){
	}
	
	public ArrayList<Task> executeCommand(String input) throws Exception {
		assert(input != "");
		CommandType command = ParserAPI.parseCommand(input);
		
		switch (command) {
			case ADD :  
				addTask(input);
				return _listTask;
				
			case DELETE : 
				deleteTask(input);
				return _listTask;
			
			case SEARCH :
				ArrayList<Task> output = searchTask(input);
				return output;
			
			case EDIT :
				editTask(input);
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
			
			case UNDONE :
				markAsUndone();
				return _listTask;
				
			case ADD_ALL : 
				addAllTask();
				return _listTask;
				
			/*case EDIT_DATE :
				editDate(input);
				return _listTask;*/
			
			default :
				throw new CommandException("Invalid Command");
		}
	}
	
	/*private void editDate(String input) {
		int index = ParserAPI.parseIndex(input);
		Task currTask = _listTask.get(index);
		String date = ParserAPI.parseDate(input);
		currTask.setDate(date);
		
		_logger.log(Level.INFO, "Date changed for " + currTask.toString());
	}*/

	private void addAllTask() throws IOException {
		for(int i = 0; i < _tempTask.size(); i++) {
			Task currTask = _tempTask.get(i);
			_listTask.add(currTask);
			StorageAPI.writeToFile(currTask.toString());
		}
		_logger.log(Level.INFO, "All task added");
	}

	public ArrayList<Task> startProgram() throws IOException {
		ArrayList<String> allFileInfo = new ArrayList<String>();
		_listTask = new ArrayList<Task>();
		allFileInfo = StorageAPI.getFromFile();
		for(int i = 0; i < allFileInfo.size(); i++) {
			ArrayList<String> information = ParserAPI.parseInformation(allFileInfo.get(i));
			Task currTask = new Task(information);
			if(!currTask.isDone()) {
				_listTask.add(currTask);
			}
		}
		sortDefault();
		
		_logger.log(Level.INFO, "Program started");
		return _listTask;
	}
	
	private void undo() throws Exception{ 
		if(!_lastCommand.isEmpty()) {
			executeCommand(_lastCommand);
			_lastCommand = new String();
		} else {
			throw new CommandException("There is no command before this.");
		}
	}

	private void markAsUndone() throws IOException {
		int index = ParserAPI.parseIndex(_lastCommand);
		Task currTask = _listTask.get(index);
		String oldDesc = currTask.getDescription();
		currTask.markAsUndone();
		String newDesc = currTask.getDescription();
		StorageAPI.editToFile(newDesc, oldDesc);
	}

	private void editTask(String input) throws IOException {
		int index = ParserAPI.parseIndex(input);
		Task oldTask = null;
		Task newTask = null;
	
		if(_lastCommand.equals("Search")) {
			assert(_searchList != null);
			oldTask = _searchList.remove(index);
			int originalIndex = _listTask.indexOf(oldTask);
			_listTask.remove(originalIndex);
			ArrayList<String> information = ParserAPI.parseInformation(input);
			newTask = new Task(information);
			_listTask.add(originalIndex, newTask);
		} else {
			oldTask = _listTask.get(index);
			_listTask.remove(index);
			ArrayList<String> information = ParserAPI.parseInformation(input);
			newTask = new Task(information);
			_listTask.add(index, newTask);
		}
		
		StorageAPI.editToFile(newTask.toString(), oldTask.toString());
		sortDefault();
		_lastCommand = new String();
		int newIndex = _listTask.indexOf(newTask) + 1;
		_lastCommand =  "EDIT " + newIndex +
						" " + oldTask.toString();
		_logger.log(Level.INFO, newTask.toString() + " is editted");
	}

	private void clearAll() throws IOException{
		_tempTask = new ArrayList<Task>(_listTask);
		_listTask.clear();
		StorageAPI.clearFile();
		
		_lastCommand = "ADD_ALL";
		_logger.log(Level.INFO, "All tasks cleared");
	}

	private void markTaskAsDone(String input) throws IOException {
		int index = ParserAPI.parseIndex(input);
		Task currTask = _listTask.remove(index);
		String oldDesc = currTask.toString();
		currTask.markAsDone();
		String newDesc = currTask.toString();
		StorageAPI.editToFile(newDesc, oldDesc);
		
		_lastCommand = "UNDONE" + index;
		_logger.log(Level.INFO, currTask.toString() + " is marked as done");
	}


	private void exitProgram() {
		System.exit(0);
	}

	private void addTask(String input) throws IOException {
		ArrayList<String> information = ParserAPI.parseInformation(input);
		Task newTask = new Task(information);
		StorageAPI.writeToFile(newTask.toString());
		_listTask.add(newTask);
		sortDefault();
		int index = _listTask.indexOf(newTask) + 1;
		_lastCommand = new String();
		_lastCommand = _lastCommand + "DELETE " + index;
		_logger.log(Level.INFO, newTask.toString() + " added");
		
	}
	
	private void deleteTask(String input) throws IOException{
		int index = ParserAPI.parseIndex(input);
		Task deletedTask = null;
		
		if(_lastCommand.equals("Search")) {
			assert(_searchList != null);
			deletedTask = _searchList.remove(index);
			_listTask.remove(deletedTask);
		} else {
			deletedTask = _listTask.remove(index);
		}
		StorageAPI.deleteFromFile(deletedTask.toString());
		_lastCommand = new String();
		_lastCommand = _lastCommand + "ADD " + deletedTask.toString();
		_logger.log(Level.INFO, deletedTask.toString() + " deleted");
		
	}
	
	private ArrayList<Task> searchTask(String input) {
		ArrayList<String> information = ParserAPI.parseInformation(input);
		String name = information.get(0);
		_searchList = new ArrayList<Task>();
		
		for(int i = 0; i < _listTask.size(); i++) {
			Task currTask = _listTask.get(i);
			if(currTask.toString().contains(name)) {
				_searchList.add(currTask);
			}
		}
		
		_lastCommand = "Search";
		_logger.log(Level.INFO, "Search " + name + " is done.");
		return _searchList;
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
