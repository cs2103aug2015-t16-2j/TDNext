package tdnext;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
//@@author Tan Wei Ming
public class StorageAPITest {
	
	@Test
	public void testfetch() throws TDNextException{
		
		File file = new File("resources/testFetch.txt");
		String absolutePath = file.getAbsolutePath();
		ArrayList<String> test = new ArrayList<String>();
		StorageAPI.fetchFromFile(absolutePath,test);
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("task 1");
		expected.add("task 2");
		expected.add("task 3");
		expected.add("task 4");
		assertEquals(test,expected);
	
	}
	
	@Test 
	public void testSync() throws TDNextException{
		
	}
	@Test
	public void testAdd() throws TDNextException {
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

}
