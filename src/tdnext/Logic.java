package tdnext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import tdnext.ParserAPI;
import tdnext.StorageAPI;
import tdnext.Task;
import tdnext.TDNextLogicAPI.CommandType;

//@@author A0125283J
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

	public ArrayList<Task> executeCommand(String input) throws TDNextException {
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

			case SEARCH_DATE :
				return searchDate(input);

			case SEARCH_TIME :
				//return searchTime(input);

			/*case EDIT_DATE :
				editDate(input);
				return _listTask;*/

			default :
				throw new TDNextException("Invalid Command");
		}
	}

	private ArrayList<Task> searchDate(String input) {
		String dateString = ParserAPI.parseDate(input);
		System.out.println(dateString);
		_searchList = new ArrayList<Task>();
		for(int i = 0; i < _listTask.size(); i++) {
			Task currTask = _listTask.get(i);
			if(currTask.getDeadline().toString().equals(dateString)){
				System.out.println(currTask.getDeadline());
				_searchList.add(currTask);
			}
		}
		return _searchList;
	}

	/*private ArrayList<Task> searchTime(String input) {
		String timeString = ParserAPI.parseTime(input);
		_searchList = new ArrayList<Task>();
		for(int i = 0; i < _listTask.size(); i++) {
			Task currTask = _listTask.get(i);
			if(currTask.getStartTime().toString().equals(timeString)){
				_searchList.add(currTask);
			}
		}
		return _searchList;
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

	public void setTheme(String theme) throws TDNextException {
		StorageAPI.setTheme(String theme);
	}

	public String getTheme() throws TDNextException {
		return StorageAPI.getTheme();
	}

	private ArrayList<Task> undo() throws TDNextException{
		if(!_lastCommandList.isEmpty()) {
			_undoMode = true;
			return executeCommand(_lastCommandList.pop());
		} else {
			throw new TDNextException("There is no command before this.");
		}
	}

	private ArrayList<Task> markAsUndone(String input) throws TDNextException {
		input = input.split(" ", 2)[1];
		ArrayList<String> information = ParserAPI.parseInformation(input);
		Task currTask = new Task(information);
		String oldDesc = currTask.toString();
		currTask.markAsUndone();
		String newDesc = currTask.toString();
		StorageAPI.editToFile(newDesc, oldDesc);
		_listTask.add(currTask);

		return returnList();
	}

	private ArrayList<Task> editTask(String input) throws TDNextException {
		int index = ParserAPI.parseIndex(input);
		Task oldTask = null;
		Task newTask = null;

		try{
			oldTask = _listTask.get(index);
		} catch (IndexOutOfBoundsException e) {
			throw new TDNextException("Invalid index");
		}
		_listTask.remove(index);
		ArrayList<String> information = ParserAPI.parseInformation(input);
		newTask = new Task(information);
		_listTask.add(index, newTask);
		StorageAPI.editToFile(newTask.toString(), oldTask.toString());

		ArrayList<Task> output = returnList();
		int newIndex = _listTask.indexOf(newTask) + 1;
		assert((newIndex > 0) && (newIndex <= _listTask.size()));
		String lastCommand =  "EDIT " + newIndex + " " + oldTask.toString();
		addLastCommand(lastCommand);

		_logger.log(Level.INFO, newTask.toString() + " is editted");

		return output;
	}

	private ArrayList<Task> clearAll() throws TDNextException{
		ArrayList<Task> tempTaskList = new ArrayList<Task>(_listTask);
		_tempTask.push(tempTaskList);
		_listTask.clear();
		StorageAPI.clearFile();
		String lastCommand = "ADD_ALL";
		addLastCommand(lastCommand);

		_logger.log(Level.INFO, "All tasks cleared");

		return _listTask;
	}

	private ArrayList<Task> markTaskAsDone(String input) throws TDNextException {
		int index = ParserAPI.parseIndex(input);
		Task oldTask = null;

		try {
			oldTask = _listTask.get(index);
		} catch (IndexOutOfBoundsException e) {
			throw new TDNextException("Invalid Index");
		}
		_listTask.remove(index);

		String oldDesc = oldTask.toString();
		oldTask.markAsDone();
		String newDesc = oldTask.toString();
		StorageAPI.editToFile(newDesc, oldDesc);
		String lastCommand = "UNDONE " + newDesc;
		addLastCommand(lastCommand);

		_logger.log(Level.INFO, oldDesc + " is marked as done");

		return returnList();
	}


	private void exitProgram() {
		System.exit(0);
	}

	private ArrayList<Task> addTask(String input) throws TDNextException {
		ArrayList<String> information = ParserAPI.parseInformation(input);
		System.out.println(information);
		Task newTask = new Task(information);
		StorageAPI.writeToFile(newTask.toString());
		_listTask.add(newTask);

		ArrayList<Task> output = returnList();
		int newIndex = _listTask.indexOf(newTask) + 1;
		assert((newIndex > 0) && (newIndex <= _listTask.size()));
		String lastCommand = "DELETE " + newIndex;
        addLastCommand(lastCommand);

		_logger.log(Level.INFO, newTask.toString() + " added");

		return output;
	}


	private ArrayList<Task> deleteTask(String input) throws TDNextException{
		int index = ParserAPI.parseIndex(input);
		Task deletedTask = null;

		try{
			deletedTask = _listTask.remove(index);
		} catch(IndexOutOfBoundsException e) {
			throw new TDNextException("Invalid Index");
		}
		StorageAPI.deleteFromFile(deletedTask.toString());
		String lastCommand = "ADD " + deletedTask.toString();
		addLastCommand(lastCommand);

		_logger.log(Level.INFO, deletedTask.toString() + " deleted");

		return returnList();
	}


	private ArrayList<Task> searchTask(String input) throws TDNextException {
		ArrayList<String> keywords = ParserAPI.parseSearch(input);
		_searchList = new ArrayList<Task>();
		populateSearchList(keywords);

		if(_undoMode) {
			_undoMode = false;
		} else if (!_searchMode){
			String lastCommand = "sort";
			_lastCommandList.push(lastCommand);
		}

		_lastSearchCommand = input;
		_searchMode = true;
		updateIndex();
		_logger.log(Level.INFO, "Search is done.");

		return _searchList;
	}

	private ArrayList<Task> sortDefault() {
		Collections.sort(_listTask, new PriorityComparator());
		_logger.log(Level.INFO, "Default sorted");
		_searchMode = false;
		updateIndex();

		return _listTask;
	}

	private ArrayList<Task> sortName() {
		Collections.sort(_listTask, new NameComparator());
		_logger.log(Level.INFO, "Sorted by name");
		_searchMode = false;
		updateIndex();

		String lastCommand = "sort";
		addLastCommand(lastCommand);

		return _listTask;
	}

	private ArrayList<Task> sortDeadline() {
		Collections.sort(_listTask, new DateComparator());
		_logger.log(Level.INFO, "Sorted by deadline");
		_searchMode = false;
		updateIndex();

		String lastCommand = "sort";
		addLastCommand(lastCommand);

		return _listTask;
	}

	private ArrayList<Task> changeDirectory(String input) throws TDNextException {
		String newDir = input.split(" ", 2)[1];
		StorageAPI.changeDir(newDir);

		return _listTask;
	}

	private void updateIndex() {
		for(int i = 0; i < _listTask.size(); i++) {
			_listTask.get(i).setIndex(i + 1);
		}
	}

	private ArrayList<Task> returnList() throws TDNextException {
		if(_searchMode) {
			searchTask(_lastSearchCommand);
			return _searchList;
		} else {
			sortDefault();
			return _listTask;
		}
	}

	private void addLastCommand(String lastCommand) {
		if(_undoMode) {
			_undoMode = false;
		} else {
			_lastCommandList.push(lastCommand);
		}
	}

	private void populateSearchList(ArrayList<String> keywords) {
		for(int j = 0; j < keywords.size(); j++) {
			String name = keywords.get(j);
			for(int i = 0; i < _listTask.size(); i++) {
				Task currTask = _listTask.get(i);
				if(currTask.toString().toLowerCase().contains(name) &&
						!_searchList.contains(currTask)) {
					_searchList.add(currTask);
				}
			}
		}
	}
}
