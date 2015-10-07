package tdnext;

import java.util.ArrayList;
import java.util.Comparator;

import tdnext.TDNextLogicAPI.ColourType;

public class Task {
	
	// Instance attributes
	private String _description = new String();
	private Date _deadline = new Date();
	private boolean _importance = false;
	private int _priorityIndex = 0;
	private boolean _done = false;
	private ColourType _colour = ColourType.WHITE;
	
	// Receives an arraylist of String which contains the line broken down
	// into various information
	public Task(ArrayList<String> information) throws IllegalArgumentException {
		_description = information.get(0);
		//_type = information.get(1);
		if(information.get(1) == "IMPORTANT") {
			_importance = true;
		}
		if(information.get(2) != "") {
			calculateDeadline(information.get(2));
		}
		calculatePriorityIndex();
		determineColourType();
	}
	
	public Task() {
	}

	private void calculateDeadline(String dateString) throws IllegalArgumentException {
		String[] dateList = dateString.split("/");
		int day = Integer.parseInt(dateList[0]);
		int month = Integer.parseInt(dateList[1]);
		try {
			int year = Integer.parseInt(dateList[2]);
			_deadline.setDate(day, month, year);
		} catch(ArrayIndexOutOfBoundsException e) {
			_deadline.setDate(day, month);
		}
		
		if (!_deadline.isValid()) {
			throw new IllegalArgumentException("Invalid Date");
		}
	}
	
	public void markAsDone() {
		_done = true;
	}
	
	public void markAsUndone() {
		_done = false;
	}
	
	@Override
	public String toString() {
		if(!_done) {
			return _description;
		} else {
			return "(x) " + _description;
		}
	}
	
	private void calculatePriorityIndex() {		
		if((!_done) && (_deadline.isValid())) {
			int difference = _deadline.difference();
			if(_importance) {
				_priorityIndex = (14 - difference + 1) * 2 + 1;
			} else {
				_priorityIndex = (14 - difference + 1) * 2;
			}
		}	
	}
	
	private void determineColourType() {
		if(_priorityIndex == 0) {
			_colour = ColourType.WHITE;
		} else if(_priorityIndex > 1){
			 _colour = ColourType.RED;
		} else if (_priorityIndex == 1) {
			_colour = ColourType.GREEN;
		} else {
			_colour = ColourType.YELLOW;
		}
	}
	
	public String getDescription() {
		return _description;
	}
	
	public Date getDeadline() {
		return _deadline;
	}
	
	public ColourType getColour() {
		return _colour;
	}
	
	public int getPriorityIndex() {
		return _priorityIndex;
	}
	
}

class NameComparator implements Comparator<Task> {
	@Override
	public int compare(Task task1, Task task2) {
        return task1.getDescription().compareToIgnoreCase(task2.getDescription());
    }
}

class PriorityComparator implements Comparator<Task> {
	@Override
	public int compare(Task task1, Task task2) {
		if(task1.getPriorityIndex() > task2.getPriorityIndex()) {
			return 1;
		} else if (task1.getPriorityIndex() < task2.getPriorityIndex()) {
			return -1;
		} else {
			return 0;
		}
	}
}

class DateComparator implements Comparator<Task> {
	@Override
	public int compare(Task task1, Task task2) {
		int difference = task1.getDeadline().difference(task2.getDeadline());
		if(difference > 0) {
			return 1;
		} else if (difference < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}