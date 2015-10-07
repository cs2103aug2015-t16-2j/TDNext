package tdnext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class StorageAPI {
	public static String dir;
	public static String outputName = "TDNext.txt"; //Name of the output text file
	public static ArrayList<String> tempClear= new ArrayList<String>(); //ArrayList to save data when user clears
	public static ArrayList<String> tempAdd= new ArrayList<String>(); //ArrayList to save data when user deletes
	public static ArrayList<String> tempDel= new ArrayList<String>(); //ArrayList to save data when user deletes
	public static ArrayList<String> data= new ArrayList<String>(); //ArrayList of strings that contain all the tasks and events, with their details
	
	//API method for the user to save the file with a different name
	public static void setName(String newName){
		outputName=newName;
	}
	
	//API method to add new tasks into text file 
	public static void writeToFile(String Task) throws IOException{
		
		data.add(Task);
		tempAdd.add(Task);
		addToFile(Task);
	}
	
	//Internal method to add a single task into the text file (added to the bottom of the text file)
	private static void addToFile(String newTask) throws IOException{
		
		FileWriter writer = new FileWriter(outputName,true);	
		writer.write(newTask + System.getProperty( "line.separator" ));
		writer.close();
		
		System.out.println(newTask +" added to: " + outputName);
	}
	
	//API method to undo add command
	public static void undoAdd() throws IOException{
		data.remove(tempAdd.get(tempAdd.size()-1));
		tempAdd.remove(tempAdd.size()-1);
		syncFile(data);
	}
	//API method to fetch all data from text file into "data" arrayList
	public static ArrayList<String> getFromFile() throws IOException{
		
		if(fileExists(outputName)){
			fetchFromFile(outputName);
			return data;
		}
		else {
			System.out.println("File does not exist yet. New file created.");
			return new ArrayList<String>();
		}
	}
			
	//Internal method to fetch data from file, store into arrayList and return this arrayList
	private static ArrayList<String> fetchFromFile(String dir) throws IOException{
			
		File f = new File(dir); 
		FileReader reader = new FileReader(f);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line;
		//Scanning all lines from the text file into arrayList
		while ((line = bufferedReader.readLine()) != null) {
			data.add(line);
		}
		reader.close();
		System.out.println("All data from file fetched into data.");
		return data;
	}
	
	//API method to update tasks, either change value or mark as done
	public static void editToFile(String newVal, String orig) throws IOException{
		int index = findIndex(data,orig);
		updateTextFile(data,index,newVal);
	}
	
	//Internal method to modify the String corresponding to the task
	private static void updateTextFile(ArrayList<String> list, int index, String newVal) throws IOException{
		
		//Clears the text file and re-populates the text file according to the updated data 
		list.set(index, newVal);
		syncFile(list);
	}
	
	//Internal method to find the index of the string in the arrayList, and return that index
	private static int findIndex(ArrayList<String> list,String term){
		
		if(list.contains(term)){
			System.out.println("found " +term + "at: " + list.indexOf(term));
			return list.indexOf(term);
		}
		//Return -1 if not found
		return -1;
	}
	
	//API method to clear text file. "data" arrayList is saved into "temp" arrayList and then cleared
	public static void clearFile() throws IOException{
		
		//Transferring tasks from data to temp
		for(int i=0;i<data.size();i++){
			tempClear.add(data.get(i));
		}
		data.clear();
		File f =  new File(outputName);
		PrintWriter writer = new PrintWriter(f);
		writer.print("");
		writer.close();
		System.out.println(outputName + " cleared.");
	}
	
	//API method to undo a clear command. data arrayList is re-populated and temp is cleared
	public static void undoClear() throws IOException{
		syncFile(tempClear);
		for(int i=0;i<tempClear.size();i++){
			data.add(tempClear.get(i));
		}
		tempClear.clear();
	}

	//API method to delete a task from text file
	public static void deleteTask(String task) throws IOException{
		data.remove(task);
		tempDel.add(task);
		syncFile(data);
	}
	
	//API method to undo delete command
	public static void undoDelete() throws IOException{
		data.add(tempDel.get(tempDel.size()-1));
		tempDel.remove(tempDel.size()-1);
		syncFile(data);
	}
	//Internal method to replace the entire text file according to the "data" arrayList
	private static void syncFile(ArrayList<String> list) throws IOException{
			
		FileWriter writer = new FileWriter(outputName,false);
		for(int i =0;i<list.size();i++){
			writer.write(list.get(i) + System.getProperty( "line.separator" ));
		}
		writer.close();
	}
	
	
	
	//Internal method to check if file already exists, and create a new one if it doesn't exist yet
	private static boolean fileExists(String dir){
		
		try  
		{
			File f = new File(outputName);
			FileWriter fileCreate = new FileWriter(outputName,true);
			if(f.exists()){
				fileCreate.close();
				return true;
			}
			else {
				fileCreate.close();
				return false;
			}
	}catch(IOException e)
		{
	    System.err.println("Error: " + e.getMessage());
	};
	return false;
	}
}	
	

