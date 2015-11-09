package tdnext;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestSystem {
	private static String EMPTY_STRING = "";
	private static String ADD_DEADLINE_TASK1_WITH_DATE = "ADD deadline1 BY 21/11/2015";
	private static String ADD_DEADLINE_TASK2_WITH_DATE = "ADD deadline2 BY 01/12/2015";
	private static String ADD_DEADLINE_TASK3_WITH_DATE_AND_TIME = "ADD deadline3 BY 21st November 8:00am";
	private static String ADD_DEADLINE_TASK4_WITH_DATE_AND_TIME = "ADD deadline4 BY 1st December 3:00pm";
	private static String ADD_DEADLINE_TASK1_WITH_DATE_FLEXI = "ADD deadline1 by 21/11/2015";
	private static String ADD_DEADLINE_TASK2_WITH_DATE_FLEXI = "ADD deadline2 BY 1st December";
	private static String ADD_DEADLINE_TASK3_WITH_DATE_AND_TIME_FLEXI = "ADD deadline3 by 21st November 8:00am";
	private static String ADD_DEADLINE_TASK4_WITH_DATE_AND_TIME_FLEXI = "ADD deadline4 by 1st Dec 3:00PM";
	private static String ADD_EVENT1_WITH_DATE = "ADD event1 ON 21/11/2015";
	private static String ADD_EVENT2_WITH_DATE = "ADD event2 ON 01/12/2015";
	private static String ADD_EVENT3_WITH_DATE_AND_TIME = "ADD event3 ON 21/11/2015 FROM 8:00am TO 12:00pm";
	private static String ADD_EVENT4_WITH_DATE_AND_TIME = "ADD event4 ON 01/12/2015 FROM 3:00pm TO 10:00pm";
	private static String ADD_EVENT1_WITH_DATE_FLEXI = "ADD event1 on 21/11/2015";
	private static String ADD_EVENT2_WITH_DATE_FLEXI = "ADD event2 on 30th November";
	private static String ADD_EVENT3_WITH_DATE_AND_TIME_FLEXI = "ADD event3 on 21/11/2015 frOM 2:00am TO 6:00pm";
	private static String ADD_EVENT4_WITH_DATE_AND_TIME_FLEXI = "ADD event4 on 01/12/2015 from 12:00pm to 10:00pm";
	private static String ADD_TASK1 = "ADD task1";
	private static String ADD_TASK2 = "ADD task2";
	private static String ADD_TASK3_WITH_IMPORTANCE = "ADD task3 IMPORTANT";
	private static String ADD_HOMEWORK1 = "ADD homework1";
	private static String ADD_HOMEWORK2 = "ADD homework2";
	private static String ADD_FLEXI_TASK1 = "AdD task1";
	private static String ADD_FLEXI_TASK2 = "adD task2";
	private static String CLEAR = "CLEAR";
	private static String CLEAR_FLEXI = "cleAR";
	private static String DELETE_INDEX0 = "DELETE 0";
	private static String DELETE_INDEX1 = "DELETE 1";
	private static String DELETE_INDEX4 = "DELETE 4";
	private static String DELETE_INDEXOUTOFBOUND = "DELETE 100";
	private static String DELETE_FLEXI_INDEX1 = "delEtE 1";
	private static String DONE_INDEX0 = "DONE 0";
	private static String DONE_INDEX1 = "DONE 1";
	private static String DONE_INDEX4 = "DONE 4";
	private static String DONE_INDEXOUTOFBOUND = "DONE 100";
	private static String DONE_NEGATIVE_INDEX1 = "DONE -1";
	private static String DONE_MAX_INDEX = "DONE " + Integer.MAX_VALUE;
	private static String DONE_MIN_INDEX = "DONE " + Integer.MIN_VALUE;
	private static String DONE_FLEXI_INDEX1 = "done 1";
	private static String EDIT_INDEX0 = "EDIT 0 taskEdit";
	private static String EDIT_INDEX1 = "EDIT 1 taskEdit";
	private static String EDIT_INDEX4 = "EDIT 4 taskEdit";
	private static String EDIT_NEGATIVE_INDEX1 = "EDIT -1 taskEdit";
	private static String EDIT_INDEXOUTOFBOUND = "EDIT 100 taskEdit";
	private static String EDIT_MAX_INDEX = "EDIT " + Integer.MAX_VALUE + " taskEdit";
	private static String EDIT_MIN_INDEX = "EDIT " + Integer.MIN_VALUE + " taskEdit";
	private static String EDIT_INDEX1_WITH_DEADLINE_TASK_WITH_DATE = "EDIT 1 deadline BY 21/11/2015";
	private static String EDIT_INDEX2_WITH_DEADLINE_TASK_WITH_DATE = "EDIT 2 deadline BY 21/11/2015";
	private static String EDIT_INDEX4_WITH_HOMEWORK3 = "EDIT 4 homework3";
	private static String EDIT_FLEXI_INDEX1 = "Edit 1 taskEdit";
	private static String SEARCH_HOME = "SEARCH home";
	private static String SEARCH_FLEXI_HOME = "search home";
	private static String SORT_DEFAULT_FLEXI = "SorT";
	private static String SORT_BY_NAME = "SORT NAME";
	private static String SORT_BY_DEADLINE = "SORT DEADLINE";
	private static String SORT_BY_NAME_FLEXI = "Sort name";
	private static String SORT_BY_DEADLINE_FLEXI = "SORT deadline";
	private static String UNDO = "UNDO";
	private static String UNDO_FLEXI = "uNDo";	

	TDNextLogicAPI _testLogic = new TDNextLogicAPI();
	ArrayList<Task> _output = new ArrayList<Task>();
	
	@Test
	public void testAddWithSimpleTask() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task1\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddWithTwoSimpleTasks() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddWithTaskWithDate() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline1 BY 21/11/2015\n" + "task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddEventWithDate() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_EVENT1_WITH_DATE);
		allInputs.add(ADD_EVENT2_WITH_DATE);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "event1 ON 21/11/2015\n" + 
									"event2 ON 01/12/2015\n" + 
									"task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddTaskWithDateAndTime() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK3_WITH_DATE_AND_TIME);
		allInputs.add(ADD_DEADLINE_TASK4_WITH_DATE_AND_TIME);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline3 BY 21st November 8:00am\n" + 
									"deadline4 BY 1st December 3:00pm\n" + 
									"task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddWithEventWithDateAndTime() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_EVENT3_WITH_DATE_AND_TIME);
		allInputs.add(ADD_EVENT4_WITH_DATE_AND_TIME);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "event3 ON 21/11/2015 FROM 8:00am TO 12:00pm\n" + 
									"event4 ON 01/12/2015 FROM 3:00pm TO 10:00pm\n" + 
									"task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}

	
	@Test
	// This is to test the boundary case of the 'within size' partition
	public void testDeleteWithIndex1() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(DELETE_INDEX1);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	// This is to test the boundary case for the 'greater than size' partition
	public void testDeleteWithIndexOutOfBound() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(DELETE_INDEXOUTOFBOUND);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testDeleteWithIndex0() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(DELETE_INDEX0);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	// This is to test the boundary case of 'within size' partition
	public void testEditWithIndex1() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(EDIT_INDEX1);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "taskEdit\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	// This is to test the boundary case of 'greater than size' partition
	public void testEditWithIndexOutOfBound() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(EDIT_INDEXOUTOFBOUND);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	// This is to test the boundary case of 'valid input' partition.
	// This is to test that the search is returning the correct output.
	public void testSearch() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(SEARCH_HOME);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "homework1\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	// This is to test the boundary case of 'valid input' partition.
	// This is to test that if there are more than one task matching the input,
	// all the tasks will be displayed.
	public void testSearchWithMoreTasks() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "homework1\n" + "homework2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	// This is to test the boundary case of 'valid index' partition
	// This is to test that the task editted is within the list from the
	// search function and not the entire list of task.
	public void testEditAfterSearchWithIndex1 () {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(EDIT_INDEX4);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "homework2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	// This is to test the boundary case of 'valid index' partition.
	// This is to test that the task deleted is within the list from the
	// search function and not the entire list of task.
	public void testDeleteAfterSearchWithIndex1() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(DELETE_INDEX4);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "homework2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	// This is to test the boundary case of 'invalid index' partition.
	public void testEditAfterSearchWithIndexOutOfBound() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(EDIT_INDEXOUTOFBOUND);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	// This is to test the boundary case of 'invalid index' partition.
	public void testDeleteAfterSearchWithIndexOutOfBound() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(DELETE_INDEXOUTOFBOUND);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testUndoAfterAdd() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(UNDO);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = EMPTY_STRING;
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testUndoAfterDelete() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(DELETE_INDEX1);
		allInputs.add(UNDO);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task1\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();			
			fail();
		}
	}
	
	@Test
	public void testUndoAfterEdit() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(EDIT_INDEX1);
		allInputs.add(UNDO);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task1\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSortDefault() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_DEADLINE_TASK2_WITH_DATE);
		allInputs.add(ADD_TASK3_WITH_IMPORTANCE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline1 BY 21/11/2015\n" +
									"task3 IMPORTANT\n" +
									"deadline2 BY 01/12/2015\n" + 
									"task1\n" + "task2\n" +
									"homework1\n" + "homework2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSortByName() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SORT_BY_NAME);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline1 BY 21/11/2015\n" + 
					                "homework1\n" + "homework2\n" + 
									"task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSortByDeadline() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_DEADLINE_TASK2_WITH_DATE);
		allInputs.add(ADD_TASK3_WITH_IMPORTANCE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SORT_BY_DEADLINE);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline1 BY 21/11/2015\n" +
									"deadline2 BY 01/12/2015\n" + 
									"task3 IMPORTANT\n" +
									"task1\n" + "task2\n" +
									"homework1\n" + "homework2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testClear() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(CLEAR);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testMarkAsDone() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(DONE_INDEX1);
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testEditWithIndex0() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(EDIT_INDEX0);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testEditWithNegativeIndex1() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(EDIT_NEGATIVE_INDEX1);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testEditWithMaxInteger() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(EDIT_MAX_INDEX);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testEditWithMinInteger() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(EDIT_MIN_INDEX);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testMarkAsDoneWithIndex0() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(DONE_INDEX0);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testMarkAsDoneWithIndexOutOfBound() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(DONE_INDEXOUTOFBOUND);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testMarkAsDoneWithNegativeIndex1() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(DONE_NEGATIVE_INDEX1);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testMarkAsDoneWithMaxIndex() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(DONE_MAX_INDEX);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testMarkAsDoneWithMinIndex() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(DONE_MIN_INDEX);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	// This is to test the boundary case of 'invalid index' partition.
	public void testMarkAsDoneAfterSearchWithIndex4() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(DONE_INDEX4);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "homework2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testEditWithIndex1WithDeadlineTask() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(EDIT_INDEX1_WITH_DEADLINE_TASK_WITH_DATE);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline BY 21/11/2015\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testEditWithIndex2WithDeadlineTask() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(EDIT_INDEX2_WITH_DEADLINE_TASK_WITH_DATE);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline BY 21/11/2015\n" + "task1\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testEditAfterSearchWithIndex4WithHomework() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(EDIT_INDEX4_WITH_HOMEWORK3);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "homework2\n" + "homework3\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testEditAfterSearchWithIndex0() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(EDIT_INDEX0);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testEditAfterSearchWithNegativeIndex1() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(EDIT_NEGATIVE_INDEX1);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testEditAfterSearchWithMaxIndex() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(EDIT_MAX_INDEX);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testEditAfterSearchWithMinIndex() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SEARCH_HOME);
		allInputs.add(EDIT_MIN_INDEX);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			fail();
		} catch (TDNextException e) {
			String desiredOutput = "Invalid Index";
			String testOutput = e.getMessage();
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		}
	}
	
	@Test
	public void testAddWithFlexibleCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_FLEXI_TASK1);
		allInputs.add(ADD_FLEXI_TASK2);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	// This is to test the boundary case of the 'within size' partition
	public void testDeleteWithFlexiCommandWithIndex1() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_FLEXI_TASK1);
		allInputs.add(ADD_FLEXI_TASK2);
		allInputs.add(DELETE_FLEXI_INDEX1);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	// This is to test the boundary case of 'within size' partition
	public void testEditWithFlexiCommandWithIndex1() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_FLEXI_TASK1);
		allInputs.add(ADD_FLEXI_TASK2);
		allInputs.add(EDIT_FLEXI_INDEX1);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "taskEdit\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	// This is to test the boundary case of 'valid input' partition.
	// This is to test that the search is returning the correct output.
	public void testSearchWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(SEARCH_FLEXI_HOME);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "homework1\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSortByNameWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SORT_BY_NAME_FLEXI);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline1 BY 21/11/2015\n" + 
					                "homework1\n" + "homework2\n" + 
									"task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSortByDeadlineWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_DEADLINE_TASK2_WITH_DATE);
		allInputs.add(ADD_TASK3_WITH_IMPORTANCE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SORT_BY_DEADLINE_FLEXI);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline1 BY 21/11/2015\n" +
									"deadline2 BY 01/12/2015\n" + 
									"task3 IMPORTANT\n" +
									"task1\n" + "task2\n" +
									"homework1\n" + "homework2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testSortDefaultWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE);
		allInputs.add(ADD_DEADLINE_TASK2_WITH_DATE);
		allInputs.add(ADD_TASK3_WITH_IMPORTANCE);
		allInputs.add(ADD_HOMEWORK1);
		allInputs.add(ADD_HOMEWORK2);
		allInputs.add(SORT_BY_DEADLINE_FLEXI);
		allInputs.add(SORT_DEFAULT_FLEXI);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline1 BY 21/11/2015\n" +
									"task3 IMPORTANT\n" +
									"deadline2 BY 01/12/2015\n" + 
									"task1\n" + "task2\n" +
									"homework1\n" + "homework2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testClearWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(CLEAR_FLEXI);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testMarkAsDoneWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(DONE_FLEXI_INDEX1);
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testUndoAfterAddWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(UNDO_FLEXI);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = EMPTY_STRING;
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddWithTaskWithDeadlineWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK1_WITH_DATE_FLEXI);
		allInputs.add(ADD_DEADLINE_TASK2_WITH_DATE_FLEXI);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline1 by 21/11/2015\n" + 
									"deadline2 BY 1st December\n" + 
									"task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddWithTaskWithDeadlineAndTimeWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_DEADLINE_TASK3_WITH_DATE_AND_TIME_FLEXI);
		allInputs.add(ADD_DEADLINE_TASK4_WITH_DATE_AND_TIME_FLEXI);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "deadline3 by 21st November 8:00am\n" + 
									"deadline4 by 1st Dec 3:00PM\n" + 
									"task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddWithEventWithDateWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_EVENT1_WITH_DATE_FLEXI);
		allInputs.add(ADD_EVENT2_WITH_DATE_FLEXI);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "event1 on 21/11/2015\n" + 
									"event2 on 30th November\n" +
									"task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testAddWithEventWithDateAndTimeWithFlexiCommand() {
		ArrayList<String> allInputs = new ArrayList<String>();
		allInputs.add(ADD_TASK1);
		allInputs.add(ADD_TASK2);
		allInputs.add(ADD_EVENT3_WITH_DATE_AND_TIME_FLEXI);
		allInputs.add(ADD_EVENT4_WITH_DATE_AND_TIME_FLEXI);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "event3 on 21/11/2015 frOM 2:00am TO 6:00pm\n" + 
									"event4 on 01/12/2015 from 12:00pm to 10:00pm\n" + 
									"task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (TDNextException e) {
			e.printStackTrace();
			fail();
		}
	}
}
