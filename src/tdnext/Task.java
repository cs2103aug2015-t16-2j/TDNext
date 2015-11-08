
package tdnext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;

import tdnext.TDNextLogicAPI.ColourType;

//@@author A0125283J
public class Task {
	private static final int URGENT_DAY = 14;

	// Instance attributes
	private String _description = new String();
	private LocalDate _deadline;
	private LocalTime _startTime;
	private LocalTime _endTime;
	private boolean _importance = false;
	private int _priorityIndex = 0;
	private int _index = 0;
	private boolean _done = false;
	private ColourType _colour = ColourType.WHITE;

	// Receives an arraylist of String which contains the line broken down
	// into various information
	public Task(ArrayList<String> information) throws TDNextException {
		assert(!information.get(0).isEmpty());
		_description = information.get(0);
		if(information.get(1) == "IMPORTANT") {
			_importance = true;
		}
		if(information.get(2).isEmpty()) {
			_deadline = LocalDate.MAX;
		} else {
			calculateDeadline(information.get(2));
		}
		if(information.get(3) == "DONE") {
			_done = true;
		}
		if(information.get(4).isEmpty()) {
		    _startTime = LocalTime.MAX;
		} else {
			_startTime = LocalTime.parse(information.get(4));
		}
		if(information.get(5).isEmpty()) {
		    _endTime = LocalTime.MAX;
		} else {
			_endTime = LocalTime.parse(information.get(4));
		}

		calculatePriorityIndex();
		determineColourType();

		Logic._logger.log(Level.INFO, this.toString() + " is created");
	}

	public Task() throws TDNextException {
		throw new TDNextException("All information are missing");
	}

	private void calculateDeadline(String dateString) throws TDNextException {
		assert(dateString.length() == 10);
		String[] dateList = dateString.split("/");
		assert(dateList[0].length() == 2);
		int day = Integer.parseInt(dateList[0]);
		assert(dateList[1].length() == 2);
		int month = Integer.parseInt(dateList[1]);
		assert(dateList[2].length() == 4);
		int year = Integer.parseInt(dateList[2]);
		try {
		    _deadline = LocalDate.of(year,  month,  day);
		} catch (DateTimeException e) {
			throw new TDNextException("Date is invalid");
		}
	}

	public void markAsDone() {
		assert(!_done);
		_done = true;
		_priorityIndex = 0;
		Logic._logger.log(Level.INFO, this.toString() + " is marked as done");
	}

	public void markAsUndone() {
		assert(_done);
		_done = false;
		calculatePriorityIndex();
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
		if((!_done) && (_deadline != LocalDate.MAX)) {
			int difference = dateDifference();
			if(difference <= 14){
				if(_importance) {
					_priorityIndex = (URGENT_DAY - difference + 1) * 2 + 1;
				} else {
					_priorityIndex = (URGENT_DAY - difference + 1) * 2;
				}
			} else if(_importance) {
				_priorityIndex = (URGENT_DAY - difference - 1) * 2 + 1;
			} else {
				_priorityIndex = (URGENT_DAY - difference - 1) * 2;
			}
		} else if(_importance) {
			_priorityIndex = 1;
		} else {
			_priorityIndex = -1;
		}
	}

	private void determineColourType() {
		if(_priorityIndex == -1) {
			_colour = ColourType.WHITE;
		} else if(_priorityIndex == 1){
			 _colour = ColourType.YELLOW;
		} else if (_priorityIndex < 0 ) {
			_colour = ColourType.GREEN;
		} else {
			_colour = ColourType.RED;
		}
	}

	private int dateDifference() {
		assert(_deadline != null);

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

	public boolean isDone() {
		return _done;
	}

	public int getIndex() {
		return _index;
	}

	public void setIndex(int index) {
		_index = index;
	}

	public LocalTime getStartTime() {
		return _startTime;
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
		int task1PriorityIndex = task1.getPriorityIndex();
		int task2PriorityIndex = task2.getPriorityIndex();

		if(task1PriorityIndex == task2PriorityIndex) {
			return compareTime(task1, task2);
		} else if((task1PriorityIndex != -1) && (task2PriorityIndex != -1)){
			if(task1.getPriorityIndex() < task2.getPriorityIndex()) {
				return 1;
			} else if (task1.getPriorityIndex() > task2.getPriorityIndex()) {
				return -1;
			}
		} else if(task1PriorityIndex == -1) {
			return 1;
		}

		return -1;
	}

	private int compareTime(Task task1, Task task2) {
		LocalTime time1 = task1.getStartTime();
		LocalTime time2 = task2.getStartTime();

		if(time1.equals(time2)){
			return 0;
		} else if (time1.isBefore(time2)){
			return -1;
		} else {
			return 1;
		}
	}
}

class DateComparator implements Comparator<Task> {
	@Override
	public int compare(Task task1, Task task2) {
		LocalDate date1 = task1.getDeadline();
		LocalDate date2 = task2.getDeadline();
		LocalTime time1 = task1.getStartTime();
		LocalTime time2 = task2.getStartTime();
		LocalDateTime dateTime1 = time1.atDate(date1);
		LocalDateTime dateTime2 = time2.atDate(date2);

		return dateTime1.compareTo(dateTime2);
	}
}