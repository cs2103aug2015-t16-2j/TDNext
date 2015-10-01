package tdnext;

import java.time.LocalDate;
import java.time.temporal.*;
import java.util.Calendar;

public class Date {
	
	private static int JANUARY = 1;
	private static int FEBRUARY = 2;
	private static int MARCH = 3;
	private static int APRIL = 4;
	private static int MAY = 5;
	private static int JUNE = 6;
	private static int JULY = 7;
	private static int AUGUST = 8;
	private static int SEPTEMBER = 9;
	private static int OCTOBER = 10;
	private static int NOVEMBER = 11;
	private static int DECEMBER = 12;
	private static boolean VALID = true;
	
	private int _year = 0000;
	private int _month = 0;
	private int _day = 0;
	
	public Date() {	
	}
	
	public Date(int dd, int mm, int yyyy) {
		_day = dd;
		_month = mm;
		_year = yyyy;
	}
	
	public Date(int dd, int mm) {
		_day = dd;
		_month = mm;
	}
	
	public int getDay() {
		return _day;
	}
	
	public int getMonth() {
		return _month;
	}
	
	public int getYear() {
		if(_year != 0) {
			return _year;
		} else {
			return Calendar.getInstance().YEAR;
		}
	}
	
	@Override
	public String toString() {
		if(_year != 0) {
			return _day + "/" + _month + "/" + _year;
		} else {
			return _day + "/" + _month;
		}
	}
	
	public boolean isValid() {
		if((_month < 1) || (_month > 12)) {
			return !VALID;
		} else if ((_day < 1) || (_day > 31)) {
				return !VALID;
		} else if ((_month == FEBRUARY) && (_day > 28)) {
				return !VALID;
		} else if ((_month == APRIL) || (_month == JUNE) ||
					(_month == SEPTEMBER) || (_month == NOVEMBER)) {
			if (_day > 30) {
				return !VALID;
			}
		}
		
		return VALID;
	}
	
	public void setDate(int dd, int mm, int yyyy) {
		_day = dd;
		_month = mm;
		_year = yyyy;
	}
	
	public void setDate(int dd, int mm) {
		_day = dd;
		_month = mm;
	}
	
	public int difference() {
		LocalDate day1 = LocalDate.now();
		LocalDate day2 = LocalDate.of(getYear(), getMonth(), getDay());
		
		return (int) ChronoUnit.DAYS.between(day1, day2);
	}
	
	public int difference (Date date2) {
		LocalDate day1 = LocalDate.of(getYear(), getMonth(), getDay());
		LocalDate day2 = LocalDate.of(date2.getYear(), date2.getMonth(), 
										date2.getDay());
		
		return (int) ChronoUnit.DAYS.between(day1, day2);
	}
}
