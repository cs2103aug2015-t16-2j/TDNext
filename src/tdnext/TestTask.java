package tdnext;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestTask {

	//@@ author A0125283J
	@Test
	public void testConstructor_ConstructorWithValidInformation(){
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("01/01/1991");
			list.add("");
			new Task(list);
		} catch (TDNextException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testGetDeadline_WithValidDate() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("01/01/1991");
			list.add("");
			new Task(list);
		} catch (TDNextException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testToString() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description IMPORTANT by 01/01/1991");
			list.add("IMPORTANT");
			list.add("01/01/1991");
			list.add("");
			Task task = new Task(list);
			assertEquals("", "description IMPORTANT by 01/01/1991",
							task.toString());
		} catch (TDNextException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void testGetDeadline_WithInvalidMonth() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("01/14/1991");
			list.add("");
			new Task(list);
			fail();
		} catch(TDNextException e) {
			assertEquals("", "Date is invalid", e.getMessage());
		}
	}

	@Test
	public void testGetDeadline_WithInvalidDay() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("00/01/1991");
			list.add("");
			new Task(list);
			fail();
		} catch(TDNextException e) {
			assertEquals("", "Date is invalid", e.getMessage());
		}
	}
}