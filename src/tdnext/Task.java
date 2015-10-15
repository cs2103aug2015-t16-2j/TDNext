package tdnext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;

import tdnext.TDNextLogicAPI.ColourType;

public class Task {
	
	// Instance attributes
	private String _description = new String();
	private LocalDate _deadline;
	private boolean _importance = false;
	private int _priorityIndex = 0;
	private boolean _done = false;
	private ColourType _colour = ColourType.WHITE;
	
	// Receives an arraylist of String which contains the line broken down
	// into various information
	public Task(ArrayList<String> information) {
		_description = information.get(0);
		if(information.get(1) == "IMPORTANT") {
			_importance = true;
		}
		if(information.get(2) != "") {
			calculateDeadline(information.get(2));
		}
		calculatePriorityIndex();
		determineColourType();
	}
	
	public Task() throws MissingInformationException {
		throw new MissingInformationException("All information are missing");
	}

	private void calculateDeadline(String dateString) throws DateTimeException {
		String[] dateList = dateString.split("/");
		int day = Integer.parseInt(dateList[0]);
		int month = Integer.parseInt(dateList[1]);
		int year = Integer.parseInt(dateList[2]);
		_deadline = LocalDate.of(day,month,year);
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
		if(!_done) {
			int difference = dateDifference();
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
	
	private int dateDifference() {
		LocalDate day1 = LocalDate.now();
		LocalDate day2 = _deadline;
		
		return (int) ChronoUnit.DAYS.between(day1, day2);
	}
	
	public String getDescription() {
		return _description;
	}
	
	public LocalDate getDeadline() {
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
		if(task1.getPriorityIndex() < task2.getPriorityIndex()) {
			return 1;
		} else if (task1.getPriorityIndex() > task2.getPriorityIndex()) {
			return -1;
		} else {
			return 0;
		}
	}
}

class DateComparator implements Comparator<Task> {
	@Override
	public int compare(Task task1, Task task2) {
		int task1PriorityIndex = task1.getPriorityIndex();
		int task2PriorityIndex = task2.getPriorityIndex();
		if(task1PriorityIndex % 2 == 0) {
			if(task2PriorityIndex % 2 == 0) {
				if(task2PriorityIndex > task1PriorityIndex) {
					return 1;
				} else if(task1PriorityIndex > task2PriorityIndex) {
					return -1;
				} else {
					return 0;
				}
			} else {
				return -1;
			}
		} else if(task2PriorityIndex % 2 == 0){
			return 1;
		} else {
			return 0;
		}
	}
}