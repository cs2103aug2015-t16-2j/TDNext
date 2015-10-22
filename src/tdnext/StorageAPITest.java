package tdnext;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class StorageAPITest {

	@Test
	public void testStorage() throws IOException {
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
