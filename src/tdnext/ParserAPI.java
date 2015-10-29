package tdnext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import tdnext.TDNextLogicAPI.CommandType;

//Edit by name --> Parse back ArrayList<String>
//Edit by  
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
	//private static Boolean isDateWord = false;
	public static ArrayList<String> task = new ArrayList<String> (5);
	public static Boolean isEdit = false;
	public static Boolean isAdd = false;
	public static Boolean isSearch = false;
	public static Boolean isDelete = false;
	public static Boolean isUndo = false;
	public static Boolean isClear = false;
	public static Boolean isDone = false;
	public static Boolean isEditDate = false;
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
		
		
		if (breakDown.length == 1 && breakDown[0].equals("sort"))
			return CommandType.SORT_DEFAULT;		
		else if (command.equals("add"))
			return CommandType.ADD;
		else if (command.contains("add_all"))
			return CommandType.ADD_ALL;
		else if (command.contains("sort")) {
			if (breakDown[1].equalsIgnoreCase("deadline"))
				return CommandType.SORT_BY_DEADLINE;
			else if (breakDown[1].equalsIgnoreCase("name"))
				return CommandType.SORT_BY_NAME;
			else
				return CommandType.SORT_DEFAULT;
		}
		else if (command.equals("undone"))
			return CommandType.UNDONE;
		else if (command.equalsIgnoreCase("done"))
			return CommandType.DONE;
		else if (command.equalsIgnoreCase("clear"))
			return CommandType.CLEAR;
		else if (command.contains("delete"))
			return CommandType.DELETE;
		else if (command.contains("edit"))
			return CommandType.EDIT;
		else if (command.contains("search"))
			return CommandType.SEARCH;
		else if (command.contains("exit"))
			return CommandType.EXIT;
		//else if (command.contains("editdate"))
			//return CommandType.EDITDATE;
		else if (command.contains("undo"))
			return CommandType.UNDO;
		//else if (command.contains("cd"))
			//return CommandType.
		return CommandType.INVALID;
	}
	
	public static ArrayList<String> parseSearch(String keyWordWithCommand) {
		String keyWord = removeCommand(keyWordWithCommand).toLowerCase();
		possibleWords = new ArrayList<String> ();
		
		ArrayList<String> temp = new ArrayList<String> ();
		
		
		//temp = ;

		//System.out.println(possibleWords);
		
		return searchFromStorage(keyWord);
	}
	
	//public static ArrayList<String> sortOut(ArrayList<String> takeIn) {
		
	//} 
	
	//Returns index number of a task to edit
	public static int parseIndex(String input) {
		String[] breakDown = input.split(" ");
        /*int number = Integer.parseInt(breakDown[1])-1;
        System.out.println(number);*/
		return Integer.parseInt(breakDown[1])-1;
	}
	
	/*public static String parseDate (String input) {
		initializeAll();
		date = removeCommand(input);
		setDate();
		
		return date;
	}*/
	
	public static ArrayList<String> parseInformation(String input) {
		
        initializeAll(); //Reset all attribute values
		
		setCurrentTime(); //Set day, month and year attributes to today's date
		
		origin = input; //origin = add/edit_2 <task>
		
		checkImportance(origin); //If origin contains IMPORTANT, remove IMPORTANT
		

		origin = removeCommand(origin); //To check if it contains add/edit and remove accordingly
		noCommand = origin;
		
		toStore(origin);
		
		origin = checkOn(origin); //To check if origin contains on and replace it with add
		
		origin.replace("\\s+", " "); //Removing duplicated spaces in string
		
		String[] sentence = origin.split(" ");
		
		checkInfo(sentence);

		return setTask(task);
	}
	
	private static ArrayList<String> searchFromStorage (String keyWord) {
		ArrayList<String> found = new ArrayList<String> ();
		//ArrayList<String> possibleWords = new ArrayList<String> ();
		
		if (storage.size() == 0)
			return null;
		else
			for (int index=0; index<storage.size(); index++)
				if (thisContains(storage.get(index).trim(), keyWord))
					found.add(storage.get(index));
		
		return removeRepeated(possibleWords);
	}
	
	private static ArrayList<String> removeRepeated(ArrayList<String> beforeEdit) {
		ArrayList<String> afterEdit = new ArrayList<String> ();
		
		if (beforeEdit.isEmpty())
			return new ArrayList<String> ();
		
		afterEdit.add(beforeEdit.get(0));
		
		for (int index=0; index<beforeEdit.size(); index++)
			if (!isInside(beforeEdit.get(index), afterEdit))
				afterEdit.add(beforeEdit.get(index));
		return afterEdit;
	}
	
	private static Boolean isInside(String thisWord, ArrayList<String> thisSentence) {
		for (int index=0; index<thisSentence.size(); index++)
			if (thisWord.equalsIgnoreCase(thisSentence.get(index)))
				return true;
		return false;
	}
	
	private static Boolean thisContains(String sentence, String keyWord) {
		String[] breakUp = sentence.split(" ");
		
		for (int index=0; index<breakUp.length; index++)
			if (similarEnough(breakUp[index], keyWord)) {
				possibleWords.add(breakUp[index]);
				return true;
			}
		
		return false;
	}
	
	private static Boolean similarEnough(String compareWord, String keyWord) {
		Boolean equalLength = sameLength(keyWord, compareWord);
		Boolean contained = compareWord.contains(keyWord);
		float pecASCII = percentageASCII(keyWord, compareWord);
		float pecChar = percentageOfSimilarity(keyWord, compareWord);
		
		//System.out.println(equalLength + " " + pecASCII + " " + pecChar);
		
		if (keyWord.toCharArray().length <= 3)
			return ((pecASCII >= 0.50) && (pecChar >= 0.50)) || contained;
		
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
		
		for (int index=0; index<charArray.length; index++)
			toStore.add(charArray[index]);
		
		return toStore;
	}
	
	private static int getValueOfWord(String aWord) {
		int value = 0;
		char[] eachChar = aWord.toCharArray();
		
		for (int index=0; index<eachChar.length; index++)
			value += getValueOfChar(eachChar[index]);
		
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
		date = "";
		isAdd = false;
		isEdit = false;
		isSearch = false;
		isDelete = false;
		isUndo = false;
		isDone = false;
		isClear = false;
		isEditDate = false;
		task = new ArrayList<String> (5);
		specificTime = "";
		isTmrw = false;
		taskDescription = "";
		origin = "";
		noCommand = "";
	}
	
	private static String removeCommand(String input) {
		String[] breakDown = input.split(" ");
		String firstWord = breakDown[0];
		
		if (firstWord.equalsIgnoreCase("add"))
			isAdd = true;
		else if (firstWord.equalsIgnoreCase("edit"))
			isEdit = true;
		else if (firstWord.equalsIgnoreCase("editdate"))
			isEditDate = true;
		else if (firstWord.equalsIgnoreCase("search"))
			isSearch = true;
		else if (firstWord.equalsIgnoreCase("delete"))
			isDelete = true;
		else if (firstWord.equalsIgnoreCase("undo"))
			isUndo = true;
		else if (firstWord.equalsIgnoreCase("clear"))
			isClear = true;
		else if (firstWord.equals("(x)"))
			isDone = true;
		
		return formNew(breakDown);
	}
	
	private static void toStore(String string) {
		storage.add(string.toLowerCase());
	}
	
	private static String formNew(String[] array) {
		String toReturn = new String();
		
		if (isAdd || isDone || isSearch) {
			for (int index=1; index<array.length; index++) {
				toReturn += (array[index] + " ");
			}
		}
		
		else if (isEdit || isDelete || isEditDate)
			for (int index=2; index<array.length; index++) {
				toReturn += array[index] + " ";
			}
		else
			for (int index=0; index<array.length; index++)
				toReturn += array[index] + " ";
		return toReturn.trim();
	}
	
	private static ArrayList<String> setTask(ArrayList<String> taks) {	
		//System.out.println(specificTime);
        task.add(noCommand);
        
		if (importance)
			task.add("IMPORTANT");
		else
			task.add("");
		
		if (date.contains("tmrw") || date.contains("tomorrow")) {
			setCurrentTime();
			setNewDate(1);
			task.add(date);
		}
		else
			task.add(date);
		
		if (isDone) {
			task.add("DONE");
		}
		else
		    task.add("");
		
		findSpecificTime();
		if (specificTime != null)
			task.add(specificTime);
		else
			task.add("");
		
		return task;
	}
	
	private static void findSpecificTime() {
		String[] breakDown = noCommand.split(" ");
		
		for (int index=0; index<breakDown.length; index++) {
			if (breakDown[index].contains("pm") || breakDown[index].contains("am"))
				if (breakDown[index].contains(":")) {
					specificTime = breakDown[index];
					break;
				}
		}
	}
	
	private static void checkInfo(String[] sentence) {
		int indexBy = indexOf("by", sentence);
		
		if (indexBy == -1) {
			if (isEditDate) {
                for (int index=0; index<sentence.length; index++)
                	if (isDateWord(sentence[index]))
                		date += (sentence[index] + " ");
                noCommand = "";
			}
			else {
			    date = "";
			}
		}
		
		else if (indexBy == 0) {
			//System.out.println("I was here");
			int index = 1;
			
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
		if (isLongMonth())
			return 31;
		else if (month == 2 && isLeapYear())
			return 29;
		else if (month == 2 && !isLeapYear())
			return 28;
		else
			return 30;
	}

	private static void setDate() {
		String initial = date;
		date = date.trim();
		
		if (date.contains("important"))
			date = date.replace("important", "").trim();
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
					if (stringDay.toCharArray().length == 1)
						stringDay = "0" + stringDay;
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
			else if (date.equals("today"))
				date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);

		}
		
		else if (length == 2 || date.contains("IMPORTANT")) {
		
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
			
			//Case: 5:00pm today
			else if (temp[0].contains(":") && (temp[1].equalsIgnoreCase("today")))
				setNewDate(0);

			//Case: today 5:00pm
			else if (temp[1].contains(":") && (temp[0].equalsIgnoreCase("today")))
				setNewDate(0);

			
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
			//System.out.println(temp[0] + " " + temp[1]);
			if (temp[0].equalsIgnoreCase("next")) {
				int toAdd = Integer.parseInt(temp[1]);
				
				if (temp[2].equalsIgnoreCase("week") || temp[2].equalsIgnoreCase("weeks"))
					setNewDate(toAdd * 7);
				else if (temp[2].equalsIgnoreCase("days") || temp[2].equalsIgnoreCase("day"))
					setNewDate(toAdd);
				else
					setNewDate(toAdd * 30);
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
			if (stringDay.toCharArray().length == 1)
				stringDay = "0" + stringDay;
			date = stringDay + "/" + Integer.toString(month) + "/" + Integer.toString(year);
		}
		/*else
			date = initial;*/
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
		
		if (word.equals("january") || word.equals("jan"))
			month = 1;
		else if (word.equals("february") || word.equals("feb"))
			month = 2;
		else if (word.equals("march") || word.equals("mar"))
			month = 3;
		else if (word.equals("april") || word.equals("apr"))
			month = 4;
		else if (word.equals("may"))
			month = 5;
		else if (word.equals("june"))
			month = 6;
		else if (word.equals("july"))
			month = 7;
		else if (word.equals("august") || word.equals("aug"))
			month = 8;
		else if (word.equals("september") || word.equals("sept"))
			month = 9;
		else if (word.equals("october") || word.equals("oct"))
			month = 10;
		else if (word.equals("november") || word.equals("nov"))
			month = 11;
		else if (word.equals("december") || word.equals("dec"))
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
		
		if ((input.contains("on") || input.contains("ON")) && index != temp.length-1) 
			return input.replace(temp[index], "by");
		return input;
	}
	
	//Checks and replace the word IMPORTANT
	private static void checkImportance(String input) {
		String[] temp = input.split(" ");
		String tempString = "";
		
		for (int index=1; index<temp.length; index++)
			if (temp[index].equalsIgnoreCase("IMPORTANT"))
				importance = true;
		
		if (importance) {
			for (int i=0; i<temp.length; i++)
				//if (!temp[i].equals("IMPORTANT"))
					tempString += (temp[i] + " ");
			origin = tempString.trim();
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
				|| word.equalsIgnoreCase("october") || word.equalsIgnoreCase("november") || word.equalsIgnoreCase("december")) || word.equalsIgnoreCase("jan") || word.equalsIgnoreCase("feb") || word.equalsIgnoreCase("mar")
				|| word.equalsIgnoreCase("apr") || word.equalsIgnoreCase("may") || word.equalsIgnoreCase("june")
				|| word.equalsIgnoreCase("july") || word.equalsIgnoreCase("aug") || word.equalsIgnoreCase("sept")
				|| word.equalsIgnoreCase("oct") || word.equalsIgnoreCase("nov") || word.equalsIgnoreCase("dec");
	}
	
	private static Boolean isDateWord(String word) {
		if (word.equalsIgnoreCase("weeks") || word.equalsIgnoreCase("week"))
			return true;
		else if (word.contains("tmrw") || word.contains("tomorrow")) {
			isTmrw = true;
			return true;
		}
		else if (word.contains("days"))
			return true;
		else if (word.equalsIgnoreCase("next"))
			return true;
		else if (word.equalsIgnoreCase("month") || word.equalsIgnoreCase("months") || word.equalsIgnoreCase("mbyth") || word.equalsIgnoreCase("mbyths"))
			return true;
		else if (word.equalsIgnoreCase("year"))
			return true;
		else if (word.equalsIgnoreCase("today"))
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
		else if (word.equalsIgnoreCase("IMPORTANT"))
			return false;
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
	
    /*public static void main(String[] args) {
		storage.add("add this is a proper task");
		while (true) {

		Scanner input = new Scanner(System.in);
		System.out.println(parseSearch(input.nextLine()));
		}
	} */
}