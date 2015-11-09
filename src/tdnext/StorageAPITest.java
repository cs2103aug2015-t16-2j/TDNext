package tdnext;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
//@@author A0122456L
public class StorageAPITest {
	
	@Test
	public void testAdd() throws TDNextException {
		StorageAPI.initialise();
		StorageAPI.clearFile();
		ArrayList<String> data1 = new ArrayList<String>();
		data1.add("task 1");
		data1.add("task 2");
		data1.add("task 3");
		data1.add("task 4");
		StorageAPI.writeToFile("task 1");
		StorageAPI.writeToFile("task 2");
		StorageAPI.writeToFile("task 3");
		StorageAPI.writeToFile("task 4");
		ArrayList<String> data2 = new ArrayList<String>();
		data2 = StorageAPI.getFromFile();
		assertEquals(data1,data2);
	}
	
	@Test
	public void testDelete() throws TDNextException {
		StorageAPI.initialise();
		StorageAPI.clearFile();
		ArrayList<String> data1 = new ArrayList<String>();
		data1.add("task 1");
		data1.add("task 2");
		data1.add("task 3");
		data1.add("task 4");
		data1.remove(3);
		StorageAPI.writeToFile("task 1");
		StorageAPI.writeToFile("task 2");
		StorageAPI.writeToFile("task 3");
		StorageAPI.writeToFile("task 4");
		StorageAPI.deleteFromFile("task 4");
		ArrayList<String> data2 = new ArrayList<String>();
		data2 = StorageAPI.getFromFile();
		assertEquals(data1,data2);
	}
	
	@Test
	public void testEdit() throws TDNextException {
		StorageAPI.initialise();
		StorageAPI.clearFile();
		ArrayList<String> data1 = new ArrayList<String>();
		data1.add("task 1");
		data1.add("task 2");
		data1.add("task 3");
		data1.add("task 4");
		data1.set(3, "new task 4");
		StorageAPI.writeToFile("task 1");
		StorageAPI.writeToFile("task 2");
		StorageAPI.writeToFile("task 3");
		StorageAPI.writeToFile("task 4");
		StorageAPI.editToFile("new task 4", "task 4");
		ArrayList<String> data2 = new ArrayList<String>();
		data2 = StorageAPI.getFromFile();
		assertEquals(data1,data2);
	}
	
	
}