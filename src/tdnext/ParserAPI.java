//@@author A0127603M
//Name: WangYulong
//Date Created: 2015-08-20
//Tutorial Group: C06
//Subject: CS2103T
//Project Name: TDNext
//Architecture: Parser

package tdnext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.lang.*;
import tdnext.TDNextLogicAPI.CommandType;
import tdnext.TDNextException;

public class ParserAPI {
	//-------------------------Attributes-----------------------------
	private static String origin = new String();
	private static String noCommand = new String();
	private static String commandWord = new String();
	private static Boolean importance = false;
	private static String date = new String();
	private static String taskDescription = new String();
	private static Boolean containsEdit = false;
	private static int year = 2015;
	private static int day = 0;
	private static int month = 0;
	private static String format = new String();
	private static Boolean isTmrw = false;
	private static String specificTime = new String();
	private static String startingTime = new String();
	private static String endingTime = new String();
	//private static Boolean isDateWord = false;
	public static ArrayList<String> task = new ArrayList<String> (5);
	public static Boolean isEdit = false;
	public static Boolean isAdd = false;
	public static Boolean isSearch = false;
	public static Boolean isSearchTime = false;
	public static Boolean isSearchDate = false;
	public static Boolean isDelete = false;
	public static Boolean isUndo = false;
	public static Boolean isClear = false;
	public static Boolean isDone = false;
	public static Boolean isEditDate = false;
	public static Boolean isCD = false;
	public static ArrayList<String> storage = new ArrayList<String> ();
	public static ArrayList<String> possibleWords = new ArrayList<String> ();
	//public static Boolean isTwoWords = false;
	//public static Boolean onlyBy = false;
	public static int foundPrev = 0;
	
	//-------------------------Constants-----------------------------
	private static final String ADD = "ADD";
	private static final String ON = "ON";
	private static final String BY = "BY";
	private static final String EDIT = "EDIT";
	private static final String IMPORTANT = "IMPORTANT";
	
	//-------------------------Main methods---------------------------
	
	public static CommandType parseCommand(String input) {
		String[] breakDown = input.split(" ");
		String command = breakDown[0].toLowerCase();
		
		
		if (breakDown.length == 1 && breakDown[0].equals("sort")) {
			return CommandType.SORT_DEFAULT;	
		}
		else if (command.equals("add")) {
			return CommandType.ADD;
		}
		else if (command.contains("add_all")) {
			return CommandType.ADD_ALL;
		}
		else if (command.contains("sort")) {
			if (breakDown[1].equalsIgnoreCase("deadline")) {
				return CommandType.SORT_BY_DEADLINE;
			}
			else if (breakDown[1].equalsIgnoreCase("name")) {
				return CommandType.SORT_BY_NAME;
			}
			else {
				return CommandType.SORT_DEFAULT;
			}
		}
		else if (command.equals("undone")) {
			return CommandType.UNDONE;
		}
		else if (command.equalsIgnoreCase("done")) {
			return CommandType.DONE;
		}
		else if (command.equalsIgnoreCase("clear")) {
			return CommandType.CLEAR;
		}
		else if (command.contains("delete")) {
			return CommandType.DELETE;
		}
		else if (command.contains("edit")) {
			return CommandType.EDIT;
		}
		else if (command.contains("search")) {
			return CommandType.SEARCH;
		}
		else if (command.contains("exit")) {
			return CommandType.EXIT;
		}
		//else if (command.contains("editdate"))
			//return CommandType.EDITDATE;	
		else if (command.contains("undo")) {
			return CommandType.UNDO;
		}
		else if (command.contains("cd")) {
			return CommandType.CHANGE_DIRECTORY;
		}
		return CommandType.INVALID;
	}
	
	public static ArrayList<String> parseSearch(String keyWordWithCommand) throws TDNextException {
		String keyWords = removeCommand(keyWordWithCommand).toLowerCase();
		possibleWords = new ArrayList<String> ();
		
		ArrayList<String> temp = new ArrayList<String> ();

		//System.out.println(possibleWords);
		
		return searchFromStorage(keyWords);
	}
	
	//public static ArrayList<String> sortOut(ArrayList<String> takeIn) {
		
	//} 
	
	//Returns index number of a task to edit
	public static int parseIndex(String input)  throws TDNextException{
		String[] breakDown = input.split(" ");
        /*int number = Integer.parseInt(breakDown[1])-1;
        System.out.println(number);*/
		try {
            return Integer.parseInt(breakDown[1])-1;
		}
		catch (NullPointerException e) {
			throw new TDNextException ("Please make sure you are editing an integer index.");
		}

	}
	
	/*public static String parseDate (String input) {
		initializeAll();
		date = removeCommand(input);
		setDate();
		
		return date;
	}*/
	
	public static ArrayList<String> parseInformation(String input) throws TDNextException {
		
        initializeAll(); //Reset all attribute values
		
		setCurrentTime(); //Set day, month and year attributes to today's date
		
		origin = input; //origin = add/edit_2 <task>
		
		checkImportance(origin); //If origin contains IMPORTANT, remove IMPORTANT

		origin = removeCommand(origin); //To check if it contains add/edit and remove accordingly
		noCommand = origin;
		
		if (specialCase(noCommand)) {
			storage.add(noCommand);
			
			ArrayList<String> temp = new ArrayList<String> ();
			
			temp.add(noCommand);
			
			if (noCommand.equalsIgnoreCase("important")) {
			    temp.add("important");
			}
			else {
				temp.add("");
			}
			
			temp.add("");
			temp.add("UNDONE");
			temp.add("");
			temp.add("");
			
			return temp;
		}
		
		toStore(origin);
		
		origin = checkOn(origin); //To check if origin contains on and replace it with add
		
		origin.replace("\\s+", " "); //Removing duplicated spaces in string
		
		String[] sentence = origin.split(" ");
		
		checkInfo(sentence);

		return setTask(task);
	}
	
	private static Boolean specialCase(String thisCase) {
		String[] find = thisCase.split(" ");
		
		return find.length == 1;
	}
	
	private static ArrayList<String> searchFromStorage (String keyWord) throws TDNextException {
		ArrayList<String> found = new ArrayList<String> ();
		//ArrayList<String> possibleWords = new ArrayList<String> ();
		
		if (storage.size() == 0) {
			throw new TDNextException("Task list is empty, there are no tasks to search from.");
		}
		/*else if (moreThanOne(keyWord)) {
			for (int index=0; index<storage.size(); index++)
				if (storage.get(index).contains(keyWord))
					possibleWords.add(keyWord);
			return possibleWords;
		}*/
		else
			for (int index=0; index<storage.size(); index++) {
				if (thisContains(storage.get(index).trim(), keyWord)) {
					found.add(storage.get(index));
				}
			}
		
		if (possibleWords.isEmpty()) {
			throw new TDNextException("There are no related tasks in the list.");
		}
		
		possibleWords.add(keyWord);
		
		return removeRepeated(possibleWords);
	}
	
	private static Boolean moreThanOne(String keyWords) {
		String[] temp = keyWords.split(" ");
		
		return temp.length == 1;
	}
	
	private static ArrayList<String> removeRepeated(ArrayList<String> beforeEdit) {
		ArrayList<String> afterEdit = new ArrayList<String> ();
		
		if (beforeEdit.isEmpty()) {
			return new ArrayList<String> ();
		}
		
		afterEdit.add(beforeEdit.get(0));
		
		for (int index=0; index<beforeEdit.size(); index++) {
			if (!isInside(beforeEdit.get(index), afterEdit)) {
				afterEdit.add(beforeEdit.get(index));
			}
		}
		
		return afterEdit;
	}
	
	private static Boolean isInside(String thisWord, ArrayList<String> thisSentence) {
		for (int index=0; index<thisSentence.size(); index++) {
			if (thisWord.equalsIgnoreCase(thisSentence.get(index))) {
				return true;
			}
		}
		
		return false;
	}
	
	private static Boolean thisContains(String sentence, String keyWord) {
		String[] breakUp = sentence.split(" ");
		
		for (int index=0; index<breakUp.length; index++) {
			if (similarEnough(breakUp[index], keyWord)) {
				possibleWords.add(breakUp[index]);
				return true;
			}
		}
		
		return false;
	}
	
	private static Boolean similarEnough(String compareWord, String keyWord) {
		Boolean equalLength = sameLength(keyWord, compareWord);
		Boolean contained = compareWord.contains(keyWord);
		float pecASCII = percentageASCII(keyWord, compareWord);
		float pecChar = percentageOfSimilarity(keyWord, compareWord);
		
		//System.out.println(equalLength + " " + pecASCII + " " + pecChar);
		
		if (keyWord.toCharArray().length <= 3) {
			return ((pecASCII >= 0.50) && (pecChar >= 0.50)) || contained;
		}
		
		return ((pecASCII >= 0.75) && (pecChar >= 0.75)) || contained;
	}
	
	private static float percentageOfSimilarity(String wordOne, String wordTwo) {
		return (float) (numCommonChar(wordOne, wordTwo) * 1.00 / wordOne.toCharArray().length);
	}
	
	private static Boolean sameLength(String wordOne, String wordTwo) {
		return (wordOne.toCharArray().length == wordTwo.toCharArray().length);
	}
	
	private static float percentageASCII(String wordOne, String wordTwo) {
		return (float) (getValueOfWord(wordOne) * 1.00 / getValueOfWord(wordTwo));
	}
	
	private static int numCommonChar(String wordOne, String wordTwo) {
		char[] charOne = wordOne.toCharArray();
		char[] charTwo = wordTwo.toCharArray();
		
		int numCommon = 0;
		
		ArrayList<Character> compareOne = toArrayList(charOne);
		ArrayList<Character> compareTwo = toArrayList(charTwo);
		
		for (int indexOne=0; indexOne<compareOne.size(); indexOne++) {
			for (int indexTwo=0; indexTwo<compareTwo.size(); indexTwo++) {
				if (compareOne.get(indexOne) == compareTwo.get(indexTwo)) {
					numCommon++;
					compareTwo.remove(indexTwo);
					break;
				}
			}
		}
		
		return numCommon;
	}
	
	private static ArrayList<Character> toArrayList(char[] charArray) {
		ArrayList<Character> toStore = new ArrayList<Character> ();
		
		for (int index=0; index<charArray.length; index++) {
			toStore.add(charArray[index]);
		}
		
		return toStore;
	}
	
	private static int getValueOfWord(String aWord) {
		int value = 0;
		char[] eachChar = aWord.toCharArray();
		
		for (int index=0; index<eachChar.length; index++) {
			value += getValueOfChar(eachChar[index]);
		}
		
		return value;
	}
	
	private static int getValueOfChar(char thisChar) {
		return (int) thisChar;
	}
	
	private static void initializeAll() {
		day = 0;
		month = 0;
		year = 2015;
		importance = false;
		date = new String();
		isAdd = false;
		isEdit = false;
		isSearch = false;
		isDelete = false;
		isUndo = false;
		isDone = false;
		isClear = false;
		isEditDate = false;
		isSearchTime = false;
		isSearchDate = false;
		task = new ArrayList<String> (5);
		specificTime = new String();
		isTmrw = false;
		isCD = false;
		taskDescription = new String();
		origin = new String();
		noCommand = new String();
		startingTime = new String();
		endingTime = new String();
	}
	
	private static String removeCommand(String input) {
		String[] breakDown = input.split(" ");
		String firstWord = breakDown[0];
		String secondWord = breakDown[1];
		
		if (firstWord.equalsIgnoreCase("add")) {
			isAdd = true;
		}
		else if (firstWord.equalsIgnoreCase("edit")) {
			isEdit = true;
		}
		else if (firstWord.equalsIgnoreCase("editdate")) {
			isEditDate = true;
		}
		else if (firstWord.equalsIgnoreCase("search")) {
			if (secondWord.equalsIgnoreCase("date")) {
				isSearchDate = true;
			}
			else if (secondWord.equalsIgnoreCase("time")) {
				isSearchTime = true;
			}
			else {
				isSearch = true;
			}
		}
		else if (firstWord.equalsIgnoreCase("delete")) {
			isDelete = true;
		}
		else if (firstWord.equalsIgnoreCase("undo")) {
			isUndo = true;
		}
		else if (firstWord.equalsIgnoreCase("clear")) {
			isClear = true;
		}
		else if (firstWord.equals("(x)")) {
			isDone = true;
		}
		else if (firstWord.equalsIgnoreCase("cd")) {
			isCD = true;
		}
		
		return formNew(breakDown);
	}
	
	private static void toStore(String string) {
		storage.add(string.toLowerCase());
	}
	
	private static String formNew(String[] array) {
		String toReturn = new String();
		
		if (isAdd || isDone || isSearch || isCD) {
			for (int index=1; index<array.length; index++) {
				toReturn += (array[index] + " ");
			}
		}
		else if (isEdit || isDelete || isEditDate || isSearchDate || isSearchTime)
			for (int index=2; index<array.length; index++) {
				toReturn += array[index] + " ";
			}
		else {
			for (int index=0; index<array.length; index++) {
				toReturn += array[index] + " ";
			}
		}
		
		return toReturn.trim();
	}
	
	private static void removeDateAndTime() {
		if (noCommand.contains("by") || noCommand.contains("on")) {
			//System.out.println(noCommand);
			String[] dismember = noCommand.split(" ");
			int index = 0;
			
			if (noCommand.contains("by")) {
				index = indexOf("by", dismember);
			}
			else {
				index = indexOf("on", dismember);
			}
			
			String temp = new String();
			int i=0;
			
			while (i != dismember.length) {
				//!dismember[i].equalsIgnoreCase("on") || !dismember[i].equalsIgnoreCase("on")
				if (!isDateWord(dismember[i]) && !dismember[i].equalsIgnoreCase("next") && (!dismember[i].equalsIgnoreCase("on") && !dismember[i].equalsIgnoreCase("by"))) {
					//System.out.println(dismember[i]);
				    temp += (dismember[i] + " ");
				}
				
				i++;
			}
			
			noCommand = temp.trim();
		}
	}
	
	private static ArrayList<String> setTask(ArrayList<String> taks) {	
		//System.out.println(specificTime);
		removeDateAndTime();
        task.add(noCommand);
        
		if (importance) {
			task.add("IMPORTANT");
	}
		else {
			task.add("");
		}
		
		if (date.contains("tmrw") || date.contains("tomorrow")) {
			setCurrentTime();
			setNewDate(1);
			task.add(date);
		}
		else {
			task.add(date);
		}
		
		if (isDone) {
			task.add("DONE");
		}
		else {
		    task.add("UNDONE");
		}
		
		findSpecificTime();
		
		/*if (specificTime != null) {
			endingTime = specificTime;
		}*/
        
		task.add(startingTime);
		task.add(endingTime);
		
		return task;
	}
	
	private static void findSpecificTime() {
		String[] breakDown = noCommand.split(" ");
		
		for (int index=0; index<breakDown.length; index++) {
			if (breakDown[index].contains("pm") || breakDown[index].contains("am")) {
				if (breakDown[index].contains(":")) {
					specificTime = breakDown[index];
					break;
				}
			}
		}
	}
	
	private static void checkInfo(String[] sentence) throws TDNextException {
		int indexBy = indexOf("by", sentence);
		
		if (indexBy == -1) {
			if (isEditDate) {
                for (int index=0; index<sentence.length; index++) {
                	if (isDateWord(sentence[index])) {
                		date += (sentence[index] + " ");
                	}
                }
                noCommand = new String();
			}		
			else {
			    date = new String();
			}
		}
		else if (indexBy == 0) {
			//System.out.println("I was here");
			int index = 1;
			
			if (isDateWord(sentence[index])) {
				while (isDateWord(sentence[index])) {
					date += (sentence[index] + " ");
					index++;
				}
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
			day += add;
			
			while (day > daysInMonth(month)) {
				day -= daysInMonth(month);
				month++;
				
				if (month > 12) {
					month -= 12;
					year++;
				}
			}
	}
	
	private static Boolean isLongMonth() {
		return (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12);
	}
	
	private static int daysInMonth(int month2) {
		if (isLongMonth()) {
			return 31;
		}
		else if (month == 2 && isLeapYear()) {
			return 29;
		}
		else if (month == 2 && !isLeapYear()) {
			return 28;
		}
		else {
			return 30;
		}
	}

	private static void setDate() throws TDNextException {
		String initial = date;
		date = date.trim();
		
		if (date.contains("important")) {
			date = date.replace("important", "").trim();
		}
		//date.replace("IMPORTANT", "").replace("\\s+", " ");
		String[] temp = date.trim().split(" ");
		int length = temp.length;
		
		if (length == 1) {
			if (date.contains("-") || date.contains("/")) {
	
				date = date.replace("-", "/");
				String[] in = date.split("/");
				
				if (in[0].toCharArray().length == 4 && in.length == 3) {
					day = Integer.parseInt(in[2]);
					String stringDay = Integer.toString(day);
					
					if (stringDay.toCharArray().length == 1) {
						stringDay = "0" + stringDay;
					}
					
					date = stringDay + "/" + in[1] + "/" + in[0];
				}
				
			}
			//Case: Tomorrow
			else if (date.contains("tomorrow") || date.contains("tmrw") || date.contains("tmw")) {
                setCurrentTime();
                day += 1;
                
                if (day > daysInMonth(month)) {
                	month++;
                	day -= (daysInMonth(month-1));
                }
                
                if (month > 12) {
                	month -= 12;
                	year++;
                }
                
                date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
			}
			
			//Case: Today
			else if (date.equals("today")) {
				date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
			}
		}		
		else if (length == 2 || date.contains("IMPORTANT")) {	
			//Cases: next week/month/year
			if (temp[0].equalsIgnoreCase("next")) {
				if (temp[1].equalsIgnoreCase("week")) {
					setNewDate(7);
				}
				//Case: next month
				else if (temp[1].equalsIgnoreCase("month")|| temp[1].equalsIgnoreCase("mbyth") || temp[1].equalsIgnoreCase("mbyths")) {
					month++;
				}
				//Case: next year
				else {
					year++;
				}
			}			
			//Case: 5:00pm today
			else if (temp[0].contains(":") && (temp[1].equalsIgnoreCase("today"))) {
				setNewDate(0);
			}
			//Case: today 5:00pm
			else if (temp[1].contains(":") && (temp[0].equalsIgnoreCase("today"))) {
				setNewDate(0);
			}
			//Case: 5:00pm tmrw/tomorrow
			else if (temp[0].contains(":") && (temp[1].equalsIgnoreCase("tomorrow") || temp[1].equalsIgnoreCase("tmrw"))) {
				setNewDate(1);
			}
			//Case: tmrw/tomorrow 5:00pm
			else if (temp[1].contains(":") && (temp[0].equalsIgnoreCase("tomorrow") || temp[0].equalsIgnoreCase("tmrw"))) {
				setNewDate(1);
			}
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
			
			if (stringDay.toCharArray().length == 1) {
				stringDay = "0" + stringDay;
			}
			
			date = stringDay + "/" + Integer.toString(month) + "/" + Integer.toString(year);
		}	
		else if (length == 3) {
			//Case: next 2 days/weeks/months
			//System.out.println(temp[0] + " " + temp[1]);
			if (temp[0].equalsIgnoreCase("next")) {
				int toAdd = Integer.parseInt(temp[1]);
				
				if (temp[2].equalsIgnoreCase("week") || temp[2].equalsIgnoreCase("weeks")) {
					setNewDate(toAdd * 7);
				}
				else if (temp[2].equalsIgnoreCase("days") || temp[2].equalsIgnoreCase("day")) {
					setNewDate(toAdd);
				}
				else {
					setNewDate(toAdd * 30);
				}
			}			
			else if (temp[2].contains("20")) {
				//Case: 1st September
				if (isDate(temp[0])) {
					convertDate(temp[0]);
					convertMonth(temp[1]);
				}				
				//Case: September 1st
				else {
					convertDate(temp[1]);
					convertMonth(temp[0]);
				}
			}
			
			String stringDay = Integer.toString(day);
			
			if (stringDay.toCharArray().length == 1) {
				stringDay = "0" + stringDay;
			}
			
			date = stringDay + "/" + Integer.toString(month) + "/" + Integer.toString(year);
		}
		else if (length == 5) {
			//Case: today/tmrw from 12:00pm to 4:00pm
			if (temp[1].equalsIgnoreCase("from") && temp[3].equalsIgnoreCase("to")) {
				if (temp[0].equalsIgnoreCase("today")) {
					date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
				}
				else {
	                setCurrentTime();
	                day += 1;
	                
	                if (day > daysInMonth(month)) {
	                	month++;
	                	day -= (daysInMonth(month-1));
	                }
	                
	                if (month > 12) {
	                	month -= 12;
	                	year++;
	                }
	                
	                date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
				}
				
				startingTime = temp[2];
				endingTime = temp[4];
			}
		}
		else if (length == 6) {
			//Case: 1st Nov/ Nov 1st from 12:00pm to 6:00pm
			if (temp[2].equalsIgnoreCase("from") && temp[4].equalsIgnoreCase("to")) {
					//Case: 1st September
					if (isDate(temp[0])) {
						convertDate(temp[0]);
						convertMonth(temp[1]);
					}				
					//Case: September 1st
					else {
						convertDate(temp[1]);
						convertMonth(temp[0]);
					}
					
					String stringDay = Integer.toString(day);
					
					if (stringDay.toCharArray().length == 1) {
						stringDay = "0" + stringDay;
					}
					
					date = stringDay + "/" + Integer.toString(month) + "/" + Integer.toString(year);
					startingTime = temp[3];
					endingTime = temp[5];
			}
		}
		/*else
			date = initial;*/
	}
	
	private static Boolean isDate(String word) {
		return ((word.contains("th") || word.contains("st") || word.contains("nd") || word.contains("rd")) && (containInt(word)));
	}
	
	private static void convertDate(String word) {
		if (word.contains("nd")) {
			day = 2;
		}
		else if (word.contains("rd")) {
			day = 3;
		}
		else if (word.contains("st")) {
			day = 1;
		}
		else {
			day = Integer.parseInt(word.replace("th", ""));
		}
	}
	
	private static void convertMonth(String word) throws TDNextException {
		word = word.toLowerCase();
		
		if (word.equals("january") || word.equals("jan")) {
			month = 1;
		}
		else if (word.equals("february") || word.equals("feb")) {
			month = 2;
		}
		else if (word.equals("march") || word.equals("mar")) {
			month = 3;
		}
		else if (word.equals("april") || word.equals("apr")) {
			month = 4;
		}
		else if (word.equals("may")) {
			month = 5;
		}
		else if (word.equals("june")) {
			month = 6;
		}
		else if (word.equals("july")) {
			month = 7;
		}
		else if (word.equals("august") || word.equals("aug")) {
			month = 8;
		}
		else if (word.equals("september") || word.equals("sept")) {
			month = 9;
		}
		else if (word.equals("october") || word.equals("oct")) {
			month = 10;
		}
		else if (word.equals("november") || word.equals("nov")) {
			month = 11;
		}
		else if (word.equals("december") || word.equals("dec")) {
			month = 12;
		}
		else {
			throw new TDNextException("Invalid month format.");
		}
	}

	private static Boolean isLeapYear() {
		if (year % 4 == 0) {
			return false;
		}
		else if (year % 100 != 0) {
			return true;
		}
		else if (year % 400 != 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	private static String checkEdit(String input) throws TDNextException {
		String[] temp = input.split(" ");
		
		if (temp[0].equalsIgnoreCase("add")) {
			return input;
		}
		
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
		
		for (; index<temp.length-1; index++) {
			if (temp[index].equalsIgnoreCase(ON)) {
				break;
			}
		}
		
		if ((input.contains("on") || input.contains("ON")) && index != temp.length-1) {
			return input.replace(temp[index], "by");
		}
		
		return input;
	}
	
	//Checks and replace the word IMPORTANT
	private static void checkImportance(String input) {
		String[] temp = input.split(" ");
		String tempString = "";
		
		for (int index=1; index<temp.length; index++) {
			if (temp[index].equalsIgnoreCase("IMPORTANT")) {
				importance = true;
			}
		}
		
		if (importance) {
			for (int i=0; i<temp.length; i++) {
				//if (!temp[i].equals("IMPORTANT"))
					tempString += (temp[i] + " ");
			}
			
			origin = tempString.trim();
		}
	}
	
	private static int indexOf(String word, String[] sentence) {
		ArrayList<Integer> position = new ArrayList<Integer> ();
		
		for (int index=0; index<sentence.length; index++) {
			if (sentence[index].equalsIgnoreCase(word)) {
				position.add(index);
			}
		}
		//System.out.println(position);
		return findRightIndex(sentence, position);
	}
	
	private static int findRightIndex(String[] sentence, ArrayList<Integer> position) {
		int length = sentence.length;
		//System.out.println(length);
        int numberOfPositions = position.size();
        
	    while (numberOfPositions != 0) {
	    	if (position.get(numberOfPositions-1) != length-1) {
	    		if (isDateWord(sentence[position.get(numberOfPositions-1) + 1])) {
	    			return position.get(numberOfPositions-1);
	    		}
	    	}
	    	
	    	numberOfPositions--;
		}
		
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
				|| word.equalsIgnoreCase("october") || word.equalsIgnoreCase("november") || word.equalsIgnoreCase("december")) 
				|| word.equalsIgnoreCase("jan") || word.equalsIgnoreCase("feb") || word.equalsIgnoreCase("mar")
				|| word.equalsIgnoreCase("apr") || word.equalsIgnoreCase("may") || word.equalsIgnoreCase("june")
				|| word.equalsIgnoreCase("july") || word.equalsIgnoreCase("aug") || word.equalsIgnoreCase("sept")
				|| word.equalsIgnoreCase("oct") || word.equalsIgnoreCase("nov") || word.equalsIgnoreCase("dec");
	}
	
	private static Boolean isDateWord(String word) {
		if (word.equalsIgnoreCase("weeks") || word.equalsIgnoreCase("week")) {
			return true;
		}
		else if (word.contains("tmrw") || word.contains("tomorrow")) {
			isTmrw = true;
			
			return true;
		}
		else if (word.equals("by") || word.equals("on")) {
			return true;
		}
		else if (word.contains("days")) {
			return true;
		}
		else if (word.equalsIgnoreCase("from")) {
			return true;
		}
		else if (word.equalsIgnoreCase("to")) {
			return true;
		}
		else if (word.equalsIgnoreCase("next")) {
			return true;
		}
		else if (word.equalsIgnoreCase("month") || word.equalsIgnoreCase("months") || word.equalsIgnoreCase("mbyth") || word.equalsIgnoreCase("mbyths")) {
			return true;
		}
		else if (word.equalsIgnoreCase("year")) {
			return true;
		}
		else if (word.equalsIgnoreCase("today")) {
			return true;
		}
		else if (word.contains("th") || word.contains("st") || word.contains("rd") || word.contains("nd")) {
			return containInt(word);
		}
		else if (containMonth(word)) {
			return true;
		}
		else if (word.matches("\\d+")) {
			return true;
		}
		else if (word.contains("/") || word.contains("-")) {
			return containInt(word);
		}
		else if (word.equals("pm") || word.equals("am")) {
			return true;
		}
		else if (word.equalsIgnoreCase("IMPORTANT")) {
			return false;
		}
		else if (word.contains(":")) {
			specificTime = word;
			return containInt(word);
		}
		else if (word.equalsIgnoreCase("tomorrow ") || word.equals("tmrw ")) {
			isTmrw = true;
			
			return true;
		}
		
		return word.contains(":");
	}
	
    public static void main(String[] args) throws TDNextException {
		storage.add("add this is a proper task");
		while (true) {

		Scanner input = new Scanner(System.in);
		System.out.println(parseInformation(input.nextLine()));
		}
	}
} 