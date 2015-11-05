package tdnext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import tdnext.ParserAPI;
import tdnext.StorageAPI;
import tdnext.Task;
import tdnext.TDNextLogicAPI.CommandType;

public class Logic {

	private ArrayList<Task> _listTask = new ArrayList<Task>();
	private Stack<String> _lastCommandList = new Stack<String>();
	private Stack<ArrayList<Task>> _tempTask = new Stack<ArrayList<Task>>();
	private ArrayList<Task> _searchList;
	public static Logger _logger = Logger.getLogger("Logic");
	private boolean _searchMode = false;
	private boolean _undoMode = false;
	private String _lastSearchCommand = new String();

	public Logic(){
	}

	public ArrayList<Task> executeCommand(String input) throws Exception {
		assert(input != "");
		CommandType command = ParserAPI.parseCommand(input);

		switch (command) {
			case ADD :
				return addTask(input);


			case DELETE :
				return deleteTask(input);

			case SEARCH :
				return searchTask(input);

			case EDIT :
				return editTask(input);

			case CLEAR :
				return clearAll();

			case DONE :
				return markTaskAsDone(input);

			case SORT_DEFAULT :
				return sortDefault();

			case SORT_BY_NAME :
				return sortName();

			case SORT_BY_DEADLINE :
				return sortDeadline();

			case UNDO:
				return undo();

			case EXIT :
				exitProgram();

			case UNDONE :
				return markAsUndone(input);

			case ADD_ALL :
				return addAllTask();

			case CHANGE_DIRECTORY :
				return changeDirectory(input);

			/*case EDIT_DATE :
				editDate(input);
				return _listTask;*/

			default :
				throw new TDNextException("Invalid Command");
		}
	}

	/*private void editDate(String input) {
		int index = ParserAPI.parseIndex(input);
		Task currTask = _listTask.get(index);
		String date = ParserAPI.parseDate(input);
		currTask.setDate(date);

		_logger.log(Level.INFO, "Date changed for " + currTask.toString());
	}*/

	private ArrayList<Task> addAllTask() throws TDNextException {
		ArrayList<Task> tempTaskList = _tempTask.pop();
		for(int i = 0; i < tempTaskList.size(); i++) {
			Task currTask = tempTaskList.get(i);
			_listTask.add(currTask);
			StorageAPI.writeToFile(currTask.toString());
		}
		_logger.log(Level.INFO, "All task added");

		return _listTask;
	}

	public ArrayList<Task> startProgram() throws TDNextException {
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

	private ArrayList<Task> undo() throws Exception{
		ArrayList<Task> output = new ArrayList<Task>();
		if(!_lastCommandList.isEmpty()) {
			_undoMode = true;
			output = executeCommand(_lastCommandList.pop());
		} else {
			throw new TDNextException("There is no command before this.");
		}

		return output;
	}

	private ArrayList<Task> markAsUndone(String input) throws Exception {
		input = input.split(" ", 2)[1];
		ArrayList<String> information = ParserAPI.parseInformation(input);
		Task currTask = new Task(information);
		String oldDesc = currTask.toString();
		currTask.markAsUndone();
		String newDesc = currTask.toString();
		StorageAPI.editToFile(newDesc, oldDesc);
		_listTask.add(currTask);

		if(_searchMode) {
			return executeCommand(_lastSearchCommand);
		} else {
			return _listTask;
		}
	}

	private ArrayList<Task> editTask(String input) throws Exception {
		int index = ParserAPI.parseIndex(input);
		Task oldTask = null;
		Task newTask = null;

		if(_searchMode) {
			assert((_searchList != null) && (_searchList.size() > 0));
			oldTask = _searchList.remove(index);
			int originalIndex = _listTask.indexOf(oldTask);
			_listTask.remove(originalIndex);
			ArrayList<String> information = ParserAPI.parseInformation(input);
			newTask = new Task(information);
			_listTask.add(originalIndex, newTask);
			_searchList.add(index, newTask);
		} else {
			oldTask = _listTask.get(index);
			_listTask.remove(index);
			ArrayList<String> information = ParserAPI.parseInformation(input);
			newTask = new Task(information);
			_listTask.add(index, newTask);
		}
		StorageAPI.editToFile(newTask.toString(), oldTask.toString());
		int newIndex = _listTask.indexOf(newTask) + 1;
		if(_undoMode) {
			_undoMode = false;
		} else {
			String lastCommand =  "EDIT " + newIndex + " " + oldTask.toString();
			_lastCommandList.push(lastCommand);
		}
		_logger.log(Level.INFO, newTask.toString() + " is editted");

		if(_searchMode) {
			return executeCommand(_lastSearchCommand);
		} else {
			sortDefault();
			return _listTask;
		}
	}

	private ArrayList<Task> clearAll() throws TDNextException{
		ArrayList<Task> tempTaskList = new ArrayList<Task>(_listTask);
		_tempTask.push(tempTaskList);
		_listTask.clear();
		StorageAPI.clearFile();

		if(_undoMode) {
			_undoMode = false;
		} else {
			String lastCommand = "ADD_ALL";
			_lastCommandList.push(lastCommand);
		}
		_logger.log(Level.INFO, "All tasks cleared");

		return _listTask;
	}

	private ArrayList<Task> markTaskAsDone(String input) throws Exception {
		int index = ParserAPI.parseIndex(input);
		Task oldTask = null;

		if(_searchMode) {
			assert((_searchList != null) && (_searchList.size() > 0));
			oldTask = _searchList.remove(index);
			int originalIndex = _listTask.indexOf(oldTask);
			_listTask.remove(originalIndex);
		} else {
			oldTask = _listTask.get(index);
			_listTask.remove(index);
		}

		String oldDesc = oldTask.toString();
		oldTask.markAsDone();
		String newDesc = oldTask.toString();
		StorageAPI.editToFile(newDesc, oldDesc);

		if(_undoMode) {
			_undoMode = false;
		} else {
			String lastCommand = "UNDONE " + newDesc;
			_lastCommandList.push(lastCommand);
		}
		_logger.log(Level.INFO, oldDesc + " is marked as done");

		if(_searchMode) {
			return executeCommand(_lastSearchCommand);
		} else {
			return _listTask;
		}
	}


	private void exitProgram() {
		System.exit(0);
	}

	private ArrayList<Task> addTask(String input) throws Exception {
		ArrayList<String> information = ParserAPI.parseInformation(input);
		Task newTask = new Task(information);
		StorageAPI.writeToFile(newTask.toString());
		_listTask.add(newTask);
		int index = _listTask.indexOf(newTask) + 1;

		if(_undoMode) {
			_undoMode = false;
		} else {
			String lastCommand = "DELETE " + index;
			_lastCommandList.push(lastCommand);
		}
		_logger.log(Level.INFO, newTask.toString() + " added");

		if(_searchMode) {
			return executeCommand(_lastSearchCommand);
		} else {
			sortDefault();
			return _listTask;
		}

	}

	private ArrayList<Task> deleteTask(String input) throws Exception{
		int index = ParserAPI.parseIndex(input);
		Task deletedTask = null;

		if(_searchMode) {
			assert(_searchList != null);
			deletedTask = _searchList.remove(index);
			_listTask.remove(deletedTask);
		} else {
			deletedTask = _listTask.remove(index);
		}
		StorageAPI.deleteFromFile(deletedTask.toString());

		if(!_undoMode) {
			String lastCommand = "ADD " + deletedTask.toString();
			_lastCommandList.push(lastCommand);
		}
		_undoMode = false;
		_logger.log(Level.INFO, deletedTask.toString() + " deleted");

		if(_searchMode) {
			return executeCommand(_lastSearchCommand);
		} else {
			return _listTask;
		}
	}

	private ArrayList<Task> searchTask(String input) {
		ArrayList<String> keywords = ParserAPI.parseSearch(input);
		_searchList = new ArrayList<Task>();

		for(int j = 0; j < keywords.size(); j++) {
			String name = keywords.get(j);
			for(int i = 0; i < _listTask.size(); i++) {
				Task currTask = _listTask.get(i);
				if(currTask.toString().toLowerCase().contains(name)) {
					_searchList.add(currTask);
				}
			}
		}

		if(_undoMode) {
			_undoMode = false;
		} else if (!_searchMode){
			String lastCommand = "sort";
			_lastCommandList.push(lastCommand);
		}

		_lastSearchCommand = input;
		_searchMode = true;
		_logger.log(Level.INFO, "Search is done.");

		return _searchList;
	}

	private ArrayList<Task> sortDefault() {
		Collections.sort(_listTask, new PriorityComparator());
		_logger.log(Level.INFO, "Default sorted");
		_searchMode = false;

		return _listTask;
	}

	private ArrayList<Task> sortName() {
		Collections.sort(_listTask, new NameComparator());
		_logger.log(Level.INFO, "Sorted by name");
		_searchMode = false;

		if(_undoMode) {
			_undoMode = false;
		} else {
			String lastCommand = "sort";
			_lastCommandList.push(lastCommand);
		}

		return _listTask;
	}

	private ArrayList<Task> sortDeadline() {
		Collections.sort(_listTask, new DateComparator());
		_logger.log(Level.INFO, "Sorted by deadline");
		_searchMode = false;

		if(_undoMode) {
			_undoMode = false;
		} else {
			String lastCommand = "sort";
			_lastCommandList.push(lastCommand);
		}

		return _listTask;
	}

	private ArrayList<Task> changeDirectory(String input) throws TDNextException {
		String newDir = input.split(" ", 2)[1];
		StorageAPI.changeDir(newDir);

		return _listTask;
	}
}
