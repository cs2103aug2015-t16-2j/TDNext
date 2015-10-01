package tdnext;
import java.util.*;

public class ParserAPI {

	enum COMMAND_TYPE {
		ADD, SORT,
		ADD_EVENT_TASK, ADD_DEADLINE_TASK, ADD_FLOATING_TASK, SORT_BY_IMPORTANCE, SORT_BY_DEADLINE,
		SORT_BY_NAME, DISPLAY, SEARCH, EXIT, DELETE, CHANGE, INVALID;
	}

	//Returns command word from user input.
	public static COMMAND_TYPE parseCommand(String input) {
		if (input.contains("ADD"))
			return COMMAND_TYPE.ADD;
		else if (input.contains("SORT"))
			return COMMAND_TYPE.SORT;
		else if (input.contains("DELETE"))
			return COMMAND_TYPE.DELETE;
		else if (input.contains("CHANGE"))
			return COMMAND_TYPE.CHANGE;
		else if (input.contains("SEARCH"))
			return COMMAND_TYPE.SEARCH;
		else if (input.contains("EXIT"))
			return COMMAND_TYPE.EXIT;
		else if (input.contains("DISPLAY"))
			return COMMAND_TYPE.DISPLAY;
		return COMMAND_TYPE.INVALID;
	}

	//Returns an ArrayList of strings in <task><IMPORTANCE><dd/mm/yyyy><DONE> format.
	public static ArrayList<String> parseInformation(String eventInfo) {
		String task_description = "";
		String deadline = "";
		String importance = "";
		
		if (eventInfo.contains(" BY ")) {
			String[] first = eventInfo.split(" BY ", 0);

			task_description = first[0];

			String[] second = first[1].split(" WITH ", 0);

			deadline = second[0];

			if (second[1].equals("IMPORTANT"))
				importance = second[1];
		}

		else if (eventInfo.contains(" ON ")) {
			String[] first = eventInfo.split(" ON ", 0);

			task_description = first[0];

			String[] second = first[1].split(" WITH ", 0);

			deadline = second[0];

			if (second[1].equals("IMPORTANT"))
				importance = second[1];
		}

		if (deadline != null) {
			if (deadline.contains("-"))
				deadline = deadline.replace("-", "/");
			else {
				String[] date = deadline.split(" ", 0);

				if (date[0].length() < date[1].length()) {
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
		else if (!eventInfo.contains("BY") && !eventInfo.contains("ON") && eventInfo.contains("WITH")){
			String[] breakDown = eventInfo.split(" WITH ", 1);

			task_description = breakDown[0];

			if (breakDown[1].equals("IMPORTANCE"))
				importance = breakDown[1];
		}

		else 
			task_description = eventInfo;

		ArrayList<String> information = new ArrayList<String>();

		information.add(task_description);
		//Add specific task description.
		information.add(importance);
		information.add(deadline);
		information.add("");

		return information;
	}

	/* public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.println("Input: ");
		String in = input.nextLine();

		System.out.println(parseInformation(in));

	}  */
}
