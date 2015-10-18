package tdnext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import tdnext.TDNextLogicAPI.CommandType;


public class ParserAPI {
	//-------------------------Attributes-----------------------------
	private static String origin = "";
	private static String noCommand = "";
	private static String commandWord = "";
	private static Boolean importance = false;
	private static String date = "";
	private static String taskDescription = "";
	private static Boolean containsEdit = false;
	private static int year = 2015;
	private static int day = 0;
	private static int month = 0;
	private static String format = "";
	//private static Boolean isDateWord = false;
	public static ArrayList<String> task = new ArrayList<String> (4);
	
	//-------------------------Constants-----------------------------
	private static final String ADD = "ADD";
	private static final String ON = "ON";
	private static final String BY = "BY";
	private static final String EDIT = "EDIT";
	private static final String IMPORTANT = "IMPORTANT";
	
	//-------------------------Main methods---------------------------
	
	public static CommandType parseCommand(String input) {
		if (input.contains("ADD"))
			return CommandType.ADD;
		/*else if (input.contains("SORT"))
			return CommandType.SORT;*/
		else if (input.contains("DELETE"))
			return CommandType.DELETE;
		else if (input.contains("CHANGE"))
			return CommandType.EDIT;
		else if (input.contains("SEARCH"))
			return CommandType.SEARCH;
		else if (input.contains("EXIT"))
			return CommandType.EXIT;
		return CommandType.INVALID;
	}
	
	//Returns index number of a task to edit
	public static int parseIndex(String input) {
		String[] breakDown = input.split(" ");

		return Integer.parseInt(breakDown[1]);
	}
	
	public static ArrayList<String> parseInformation(String input) {
		setCurrentTime();
		origin = input;
		checkImportance(origin);
		origin = checkEdit(origin.replace("\\s+", ""));
		origin = checkOn(origin);
		origin.replace("\\s+", ""); //Removing duplicated spaces in string
		String[] sentence = origin.split(" ");
		
		checkInfo(sentence);
		
		task.add(input.replace("IMPORTANT", "").replace("add", "").replace("\\s+", "").trim());
		if (importance)
			task.add("IMPORTANT");
		else
			task.add(null);
		task.add(date);
		task.add(null);
		
		return task;
	}
	
	private static void checkInfo(String[] sentence) {
		int indexBy = indexOf("by", sentence);
		
		if (indexBy == 1) {
			int index = 2;
			
			if (isDateWord(sentence[index]))
				while (isDateWord(sentence[index])) {
					date += (sentence[index] + " ");
					index++;
				}
			else {
				while (!isDateWord(sentence[index])) {
					date += (sentence[index] + " ");
					index++;
				}
				while (isDateWord(sentence[index])) {
					date += (sentence[index] + " ");
					index++;
				}
			}
			date.trim();
			
			while (index < sentence.length) {
				taskDescription += (sentence[index] + " ");
				index++;
			}
			taskDescription.trim();
		}
		else {
			for (int index=1; index<indexBy; index++)
				taskDescription += (sentence[index] + " ");
			taskDescription.trim();
			
			for (int index=indexBy+1; index<sentence.length; index++)
				date += (sentence[index] + " ");
			date.trim();
		}
		
		setDate();
	}
	
	private static void setNewDate(int add) {
			if (isLeapYear() && month == 2) {
				if ((day + add) > 29) {
					month++;
					day -= (29 - add);
				}
				else
					day += add;
			}
			else if (!isLeapYear() && month == 2) {
				if ((day + add) > 28) {
					month++;
					day -= (28 - add);
				}
				else
					day += add;
			}
			else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				
				if ((day + add) > 31) {
					month ++;
					day -= (31 - add);
				}
				else
					day += add;
			}
			else {
				if ((day + add) > 30) {
					month ++;
					day -= (30 - add);
				}
				else
					day += add;
			}
			
			//Check for overflow
			if (month > 12) {
				System.out.println("2");
				month -= 12;
				year++;
			}
	}
	
	private static void setDate() {
		String[] temp = date.split(" ");
		int length = temp.length;
		
		if (length == 1) {
			if (date.contains("-") || date.contains("/")) {
				date = date.replace("-", "/");
				String[] in = date.split("/");
				
				if (in[0].toCharArray().length == 4 && in.length == 3) {
					day = Integer.parseInt(in[2]);
					String stringDay = Integer.toString(day);
					if (stringDay.toCharArray().length == 1)
						stringDay = "0" + stringDay;
					date = stringDay + "/" + in[1] + "/" + in[0];
				}
			}
			//Case: tomorrow
			else 
				setNewDate(1);			
		}
		
		else if (length == 2) {
		
			//Cases: next week/month/year
			if (temp[0].equalsIgnoreCase("next")) {
				if (temp[1].equalsIgnoreCase("week")) {
					setNewDate(7);
				}
				//Case: next month
				else if (temp[1].equalsIgnoreCase("month")|| temp[1].equalsIgnoreCase("mbyth") || temp[1].equalsIgnoreCase("mbyths"))
					month++;
				
				//Case: next year
				else
					year++;
			}
			
			//Case: 5:00pm tmrw/tomorrow
			else if (temp[0].contains(":") && (temp[1].equalsIgnoreCase("tomorrow") || temp[1].equalsIgnoreCase("tmrw")))
				setNewDate(1);

			//Case: tmrw/tomorrow 5:00pm
			else if (temp[1].contains(":") && (temp[0].equalsIgnoreCase("tomorrow") || temp[0].equalsIgnoreCase("tmrw")))
				setNewDate(1);
			
			//Case: 1st September
			else if (isDate(temp[0])) {
				convertDate(temp[0]);
				convertMonth(temp[1]);
			}
			
			//Case: September 1st
			else {
				convertDate(temp[1]);
				convertMonth(temp[0]);
			}
			String stringDay = Integer.toString(day);
			if (stringDay.toCharArray().length == 1)
				stringDay = "0" + stringDay;
			date = stringDay + "/" + Integer.toString(month) + "/" + Integer.toString(year);
		}
		
		else if (length == 3) {
			//Case: next 2 days/weeks/months
			if (temp[0].equalsIgnoreCase("next")) {
				int toAdd = Integer.parseInt(temp[1]);
				
				if (temp[2].equalsIgnoreCase("week") || temp[2].equalsIgnoreCase("weeks"))
					setNewDate(toAdd * 7);
				else if (temp[2].equalsIgnoreCase("days") || temp[2].equalsIgnoreCase("day"))
					setNewDate(toAdd);
				else
					setNewDate(toAdd * 30);
			}
			String stringDay = Integer.toString(day);
			if (stringDay.toCharArray().length == 1)
				stringDay = "0" + stringDay;
			date = stringDay + "/" + Integer.toString(month) + "/" + Integer.toString(year);
		}
		
	}
	
	private static Boolean isDate(String word) {
		return ((word.contains("th") || word.contains("st") || word.contains("nd") || word.contains("rd")) && (containInt(word)));
	}
	
	private static void convertDate(String word) {
		if (word.contains("nd"))
			day = 2;
		else if (word.contains("rd"))
			day = 3;
		else if (word.contains("st"))
			day = 1;
		else
			day = Integer.parseInt(word.replace("th", ""));
	}
	
	private static void convertMonth(String word) {
		word = word.toLowerCase();
		
		if (word.equals("january"))
			month = 1;
		else if (word.equals("february"))
			month = 2;
		else if (word.equals("march"))
			month = 3;
		else if (word.equals("april"))
			month = 4;
		else if (word.equals("may"))
			month = 5;
		else if (word.equals("june"))
			month = 6;
		else if (word.equals("july"))
			month = 7;
		else if (word.equals("august"))
			month = 8;
		else if (word.equals("september"))
			month = 9;
		else if (word.equals("october"))
			month = 10;
		else if (word.equals("november"))
			month = 11;
		else if (word.equals("december"))
			month = 12;
	}
	
	private static Boolean isLeapYear() {
		if (year % 4 == 0)
			return false;
		else if (year % 100 != 0)
			return true;
		else if (year % 400 != 0)
			return false;
		else
			return true;
	}
	
	private static String checkEdit(String input) {
		String[] temp = input.split(" ");
		
		if (temp[0].equalsIgnoreCase("add"))
			return input;
		
		if (!temp[0].equalsIgnoreCase("add")) {
			containsEdit = true;
			String toRemove = temp[0] + " " + parseIndex(input) + " ";
			return input.replace(toRemove, "add ");
		}
		return input;
	}
	
	private static String checkOn(String input) {
		String[] temp = input.split(" ");
		int index = 1;
		
		for (; index<temp.length-1; index++)
			if (temp[index].equalsIgnoreCase(ON))
				break;
		
		if ((input.contains("on") || input.contains("ON")) && index != temp.length -1) 
			return input.replace(temp[index], "by");
		return input;
	}
	
	private static void checkImportance(String input) {
		if (input.contains("IMPORTANT ")) {
			origin.replace("IMPORTANT ", "");
			importance = true;
		}
		else if (input.contains("IMPORTANT")) {
			origin.replace(" IMPORTANT", "");
			importance = true;
		}
	}
	
	private static int indexOf(String word, String[] sentence) {
		for (int index=0; index<sentence.length; index++)
			if (sentence[index].equalsIgnoreCase(word))
				return index;
		return -1;
	}
	
	private static void setCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date thisDate = new Date();
		String today = dateFormat.format(thisDate);
		String[] separate = today.split("/");
		
		day = Integer.parseInt(separate[0]);
		month = Integer.parseInt(separate[1]);
		year = Integer.parseInt(separate[2]);
	}
	
	//Checks if a number is present in a String
	private static Boolean containInt(String word)  {
		return word.matches(".*\\d+.*");
	}
	
	private static Boolean containMonth(String word) {
		return (word.equalsIgnoreCase("january") || word.equalsIgnoreCase("feburary") || word.equalsIgnoreCase("march")
				|| word.equalsIgnoreCase("april") || word.equalsIgnoreCase("may") || word.equalsIgnoreCase("june")
				|| word.equalsIgnoreCase("july") || word.equalsIgnoreCase("august") || word.equalsIgnoreCase("september")
				|| word.equalsIgnoreCase("october") || word.equalsIgnoreCase("november") || word.equalsIgnoreCase("december"));
	}
	
	private static Boolean isDateWord(String word) {
		if (word.equalsIgnoreCase("weeks") || word.equalsIgnoreCase("week"))
			return true;
		else if (word.contains("days"))
			return true;
		else if (word.equalsIgnoreCase("next"))
			return true;
		else if (word.equalsIgnoreCase("month") || word.equalsIgnoreCase("months") || word.equalsIgnoreCase("mbyth") || word.equalsIgnoreCase("mbyths"))
			return true;
		else if (word.equalsIgnoreCase("year"))
			return true;
		else if (word.contains("th") || word.contains("st") || word.contains("rd") || word.contains("nd")) {
			return containInt(word);
		}
		else if (containMonth(word))
			return true;
		else if (word.matches("\\d+"))
			return true;
		else if (word.contains("/") || word.contains("-"))
			return containInt(word);
		else if (word.equals("pm") || word.equals("am"))
			return true;
		else if (word.contains("pm") || word.contains("am"))
			return containInt(word);
		else if (word.equalsIgnoreCase("tomorrow") || word.equals("tmrw"))
			return true;
		return word.contains(":");
	}
	
	/*public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println(parseInformation(input.nextLine()));
	}*/
}
