package tdnext;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestLogic {
	TDNextLogicAPI _testLogic = new TDNextLogicAPI();
	ArrayList<Task> _output = new ArrayList<Task>();

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAddWithSimpleTask() {
		String input = "ADD task1";
		try {
			_output = _testLogic.executeCommand(input);
			String desiredOutput = "task1\n";
			String testOutput = new String();
			for(int i = 0; i < _output.size(); i++) {
				testOutput = testOutput + _output.get(i).toString() + "\n";
			}
			assertEquals("", desiredOutput, testOutput);
		} catch (Exception e) {
			fail();
		}
	}

}
