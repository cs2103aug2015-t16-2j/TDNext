package tdnext;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestTask {
	
	@Test
	public void testConstructor_DefaultConstructor_ShouldHaveException() {
		try {
			new Task();
			fail();
		} catch (MissingInformationException e) {
			assertEquals("", "Missing all information", e.getMessage());
		}
	}
	
	@Test
	public void testConstructor_ConstructorWithValidInformation_ShouldNotHaveException(){
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("01/01/1991");
			new Task(list);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	public void testGetDeadline_WithValidDate_ShouldReturnValidDeadline() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("01/01/1991");
			new Task(list);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	public void testToString() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("01/01/1991");
			Task task = new Task(list);
			assertEquals("", "description BY 1/1/1991 IMPORTANT",
							task.toString());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	public void testGetDeadline_WithInvalidMonth_ShouldThrowIllegalArgumentException() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("01/14/1991");
			new Task(list);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals("", "Invalid Date", e.getMessage());
		}
	}
	
	@Test
	public void testGetDeadline_WithInvalidDay_ShouldThrowIllegalArgumentException() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("00/01/1991");
			new Task(list);
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals("", "Invalid Date", e.getMessage());
		}
	}
	
	@Test
	public void testGetDeadline_WithoutYear_ShouldReturnValidDateWithCurrentYear() {
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.add("description");
			list.add("IMPORTANT");
			list.add("01/01");
			Task task = new Task(list);
			assertEquals("", "description BY 1/1 IMPORTANT",
							task.toString());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	public void testCalcaulateDaysDifference_MultipleDates_ShouldReturnCorrectDays() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("description");
		list.add("IMPORTANT");
		list.add("01/10/2015");
		new Task(list);
		
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("description");
		list1.add("IMPORTANT");
		list1.add("01/01/2016");
		new Task(list1);
		
	}
	
	@Test
	public void testConstructor_WithDifferentKindsOfInputs_ShouldNotHaveErrors() {
		ArrayList<Task> output = new ArrayList<Task>();
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			list.add("IMPORTANT and 1 DAY TO DEADLINE");
			list.add("IMPORTANT");
			list.add("28/09/2015");
			output.add(new Task(list));
			list.clear();
			list.add("Not Important and 1 DAY TO DEADLINE");
			list.add("");
			list.add("28/09");
			output.add(new Task(list));
			list.clear();
			list.add("Not Important and 14 DAYS TO DEADLINE");
			list.add("");
			list.add("11/10/2015");
			output.add(new Task(list));
			list.clear();
			list.add("IMPORTANT and NO DEADLINE");
			list.add("IMPORTANT");
			list.add("");
			output.add(new Task(list));
			list.clear();
			list.add("IMPORTANT and 15 DAYS TO DEADLINE");
			list.add("IMPORTANT");
			list.add("12/10/2015");
			output.add(new Task(list));
			list.clear();
			list.add("Not Important and NO DEADLINE");
			list.add("");
			list.add("");
			output.add(new Task(list));
			for(int i = 0; i < output.size(); i++) {
				System.out.println(output.get(i).toString());
				System.out.println(output.get(i).getPriorityIndex());
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			fail();
		}
		
	}
}