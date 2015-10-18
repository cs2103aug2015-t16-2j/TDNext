package tdnext;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestLogic {
	private static String EMPTY_STRING = "";
	
	TDNextLogicAPI _testLogic = new TDNextLogicAPI();
	ArrayList<Task> _output = new ArrayList<Task>();

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddWithTwoSimpleTask() {
		ArrayList<String> allInputs = new ArrayList<String>();
		String input1 = "ADD task1";
		allInputs.add(input1);
		
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
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testAddWithAnotherSimpleTask() {
		ArrayList<String> allInputs = new ArrayList<String>();
		String input1 = "ADD task1";
		allInputs.add(input1);
		String input2 = "ADD task2";
		allInputs.add(input2);
		
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
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testAddWithTaskWithDate() {
		ArrayList<String> allInputs = new ArrayList<String>();
		String input1 = "ADD task1";
		allInputs.add(input1);
		String input2 = "ADD task2";
		allInputs.add(input2);
		String input3 = "ADD task3 BY 30/10/2015";
		allInputs.add(input3);
		
		try {
			for(int i = 0; i < allInputs.size(); i++){
				_output = _testLogic.executeCommand(allInputs.get(i));
			}
			String desiredOutput = "task3 BY 30/10/2015\n" + "task1\n" + "task2\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals(EMPTY_STRING, desiredOutput, testOutput);
		} catch (Exception e) {
			fail();
		}
	}

}
