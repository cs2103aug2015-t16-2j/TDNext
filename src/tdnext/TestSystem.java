package tdnext;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestSystem {
	private static String EMPTY_STRING = "";
	private static String ADD_DEADLINE_TASK1_WITH_DATE = "ADD deadline1 BY 21/11/2015";
	private static String ADD_DEADLINE_TASK2_WITH_DATE = "ADD deadline2 BY 01/12/2015";
	private static String ADD_TASK1 = "ADD task1";
	private static String ADD_TASK2 = "ADD task2";
	private static String ADD_TASK3_WITH_IMPORTANCE = "ADD deadline1 IMPORTANT";
	private static String ADD_HOMEWORK1 = "ADD homework1";
	private static String ADD_HOMEWORK2 = "ADD homework2";
	private static String CLEAR = "CLEAR";
	private static String DELETE_INDEX0 = "DELETE 0";
	private static String DELETE_INDEX1 = "DELETE 1";
	private static String DELETE_INDEX4 = "DELETE 4";
	private static String DELETE_INDEXOUTOFBOUND = "DELETE 100";
	private static String DONE_INDEX1 = "DONE 1";
	private static String EDIT_INDEX1 = "EDIT 1 taskEdit";
	private static String EDIT_INDEX4 = "EDIT 4 taskEdit";
	private static String EDIT_INDEXOUTOFBOUND = "EDIT 100 taskEdit";
	private static String SEARCH_HOME = "SEARCH home";
	private static String SORT_BY_NAME = "SORT NAME";
	private static String SORT_BY_DEADLINE = "SORT DEADLINE";
	private static String UNDO = "UNDO";

	
	TDNextLogicAPI _testLogic = new TDNextLogicAPI();
	ArrayList<Task> _output = new ArrayList<Task>();
	
	@Test
	// This is to test that the add function can work.
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
	// This is to test that adding the second task will not overwrite the first
	// task
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
	// This is to test that the task with date will retain the date
	// as part of its description
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
			String desiredOutput = "homework1\n" + "homework2\n" + 
									"task1\n" + "task2\n" + 
									"deadline1 BY 21/11/2015\n";
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
	
}
