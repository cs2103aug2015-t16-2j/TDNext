package tdnext;
import java.util.*;

import tdnext.TDNextLogicAPI.CommandType;

public class ParserAPI {
	//Returns command word from user input.
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
    
	
	//Find the index of the task which will be edited.
	public static int parseIndex(String input) {
		String[] breakdownString = input.split(" ");
		
		return Integer.parseInt(breakdownString[1]);
	}
	
	//Returns an ArrayList of strings in <task><IMPORTANCE><dd/mm/yyyy><DONE> format.
	public static ArrayList<String> parseInformation(String input) {
		String copy = input;
		String eventInfo = input;
		String task_description = "";
		String deadline = "";
		String importance = checkImportance(eventInfo);
	
		if (importance != null) {
			if (eventInfo.contains("IMPORTANT "))
				eventInfo.replace("IMPORTANT ", "");
			else
			    eventInfo.replace(" IMPORTANT", "");
		}
		if (eventInfo.contains("WITH "))
			eventInfo.replace("WITH ", "");
		
		String[] tempEvent = eventInfo.split(" ");
		
		ArrayList<String> brokenEvent = new ArrayList<String> ();

		for (int i=0; i<=tempEvent.length-1; i++)
			if (!tempEvent[i].equals("WITH"))
				brokenEvent.add(tempEvent[i]);
		
		if (!eventInfo.contains("ADD")) {
			eventInfo.replace((brokenEvent.get(0) + " " + brokenEvent.get(1) + " "), "ADD");
			copy = input.replace((brokenEvent.get(0) + " " + brokenEvent.get(1) + " "), "ADD");
		}
		
		if (eventInfo.contains("BY") || eventInfo.contains("ON")) {
			int indexBY = getIndexBY(brokenEvent);
			int indexADD = getIndexADD(brokenEvent);

			if (indexADD<indexBY && indexBY-indexADD != 1) {
				for (int index=indexADD+1; index<indexBY; index++)
					task_description += (brokenEvent.get(index) + " ");
				for (int index=indexBY+1; index<=brokenEvent.size()-1; index++)
					deadline += (brokenEvent.get(index) + " ");
			}
			else if (indexADD>indexBY && indexADD-indexBY != 1) {
				for (int index=indexBY+1; index<indexADD; index++)
					deadline += (brokenEvent.get(index) + " ");
				for (int index=indexADD+1; index<=brokenEvent.size()-1; index++)
					task_description += (brokenEvent.get(index) + " ");
			}
			else {
				if (brokenEvent.get(indexBY+1).contains("-") || brokenEvent.get(indexBY+1).contains("/")) {
					deadline = brokenEvent.get(indexBY+1);
					
					for (int taskIndex=indexBY+2; taskIndex<brokenEvent.size(); taskIndex++)
						task_description += (brokenEvent.get(taskIndex) + " ");
				}
				else if (brokenEvent.get(indexBY+3).contains("20")) {
					System.out.println("1");
					for (int deadlineIndex=indexBY+1; deadlineIndex<=(indexBY+3); deadlineIndex++)
						deadline += (brokenEvent.get(deadlineIndex) + " ");
					for (int taskIndex=indexBY+4; taskIndex<brokenEvent.size(); taskIndex++)
						task_description += (brokenEvent.get(taskIndex) + " ");
				}
				else {
					for (int deadlineIndex=indexBY+1; deadlineIndex<=(indexBY+2); deadlineIndex++)
						deadline += (brokenEvent.get(deadlineIndex) + " ");
					for (int taskIndex=indexBY+3; taskIndex<brokenEvent.size(); taskIndex++)
						task_description += (brokenEvent.get(taskIndex) + " ");
				}
			}
		}
		else if (!(eventInfo.contains("BY") && !eventInfo.contains("ON"))) {
			ArrayList<String> toReturn = new ArrayList<String> ();
			
			toReturn.add(copy.replace("ADD","").trim());
			if (copy.contains("IMPORTANT"))
				toReturn.add("IMPORTANT");
			else
				toReturn.add("");
			toReturn.add("");
			toReturn.add("");
			
			return toReturn;
		}
		
		else {
			for (int index=0; index<brokenEvent.size()-1; index++)
				if (!brokenEvent.get(index).equals("ADD"))
					deadline += brokenEvent.get(index);
		}

		if (deadline != null && !deadline.contains("/")) {
			if (deadline.contains("-"))
				deadline = deadline.replace("-", "/");

			else {
				String[] date = deadline.split(" ");
				char[] furtherBreakdown = date[1].toCharArray();
				
				if (furtherBreakdown.length <= 2 && (date[1].contains("0") || date[1].contains("1"))) {
					if (date.length == 2) {
					deadline = date[0] + "/" + date[1];
					}
					else
						deadline = date[0] + "/" + date[1] + "/" + date[2];
				}

				else {
					if (date[0].trim().length() < date[1].trim().length()) {

						String switchDateNMonth = date[1];

						date[1] = date[0];

						date[0] = switchDateNMonth;
					}

					String month = date[0].toLowerCase();
					deadline = "";

					if (month.equals("january"))
						month = "01";
					else if (month.equals("february"))
						month = "02";
					else if (month.equals("march"))
						month = "03";
					else if (month.equals("april"))
						month = "04";
					else if (month.equals("may"))
						month = "05";
					else if (month.equals("june"))
						month = "06";
					else if (month.equals("july"))
						month = "07";
					else if (month.equals("august"))
						month = "08";
					else if (month.equals("september"))
						month = "09";
					else if (month.equals("october"))
						month = "10";
					else if (month.equals("november"))
						month = "11";
					else if (month.equals("december"))
						month = "12";

					String day = date[1];
					char[] count = day.toCharArray();

					if (count.length == 3) 
						day = "0" + day.substring(0, 1);
					else if (count.length == 4)
						day = day.substring(0, 2);

					deadline = day + "/" + month;
				}
			}
		}

		ArrayList<String> information = new ArrayList<String>();

		information.add((copy.replace("IMPORTANT", "").replace("ADD", "").trim()));
		//Add specific task description.
		if (importance != null)
			information.add(importance);
		else
			information.add("");
		information.add(deadline.trim());
		information.add("");
		return information;
	}
	
	
	/* --------------------------------Other Sub-methods----------------------------------- */
	
	//To check if word "IMPORTANT" is present in a string.
	private static String checkImportance(String eventInfo) {
		if (eventInfo.contains("IMPORTANT"))
			return "IMPORTANT";
		return null;
	}
	
	
	//To find the index of word "BY"
	private static int getIndexBY(ArrayList<String> brokenEvent) {
		for (int index=0; index<brokenEvent.size()-1; index++)
			if (brokenEvent.get(index).equals("BY"))
				return index;
		return 0;
	}
	
	//To find the index of word "ADD"
	private static int getIndexADD(ArrayList<String> brokenEvent) {
		for (int index=0; index<brokenEvent.size()-1; index++)
			if (brokenEvent.get(index).equals("ADD"))
				return index;
		return 0;
	}
	

    //For testing purposes.
	
	 public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.println("Input: ");
		String in = input.nextLine();

		System.out.println(parseInformation(in));
	 }
}
