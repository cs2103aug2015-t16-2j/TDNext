package tdnext;

import java.io.IOException;
import java.util.ArrayList;

import tdnext.ParserAPI.COMMAND_TYPE;
import tdnext.TDNextLogicAPI.CommandType;

public class TDNext {
	
	private ArrayList<Task> _listTask = new ArrayList<Task>();
	
	public TDNext(){
	}
	
	public ArrayList<Task> executeCommand(String input) {
		COMMAND_TYPE command = ParserAPI.parseCommand(input);
		
		switch (command){
		case ADD :  addTask(input);
					return _listTask;
		case DELETE : 
						break;
		}
		
		return _listTask;
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
	
	/*private void deleteTask(String input) {
		ArrayList<String> information = ParserAPI.parseInformation(input);
		int index = searchTask(information.get(0));
		Task removedTask = _listTask.remove(index);
		StorageAPI.deleteFromFile(removedTask.toString());
	}
	
	private int searchTask(String name) {
		for(int i = 0; i < _listTask.size(); i++) {
			if(_listTask.get(i).toString().equals(name)) {
				return i;
			}
		}
	}*/
}
