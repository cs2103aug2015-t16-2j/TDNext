package tdnext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageAPI {
	public static String dir;
	public static String outputName;
	private static Logger storageLog = Logger.getLogger("Storage");
	public static ArrayList<String> tempClear= new ArrayList<String>(); //ArrayList to save data when user clears
	public static ArrayList<String> tempAdd= new ArrayList<String>(); //ArrayList to save data when user deletes
	public static ArrayList<String> tempDel= new ArrayList<String>(); //ArrayList to save data when user deletes
	public static ArrayList<String> settings = new ArrayList<String>();
	public static ArrayList<String> data= new ArrayList<String>(); //ArrayList of strings that contain all the tasks and events, with their details
	
	//API method for the user to save the file with a different name
	public static void setName(String newName) throws IOException{
		File f = new File(dir+outputName);
		f.renameTo(new File(dir+newName));
		outputName=newName;
		settings.set(1, newName);
		saveSettings();
		storageLog.log(Level.INFO,"File output name set to : "+outputName);
	}
	
	//API method for user to change directory of the output text file
	public static void changeDir(String newDir) throws IOException{
		
		File f = new File(dir+outputName);
		f.delete();
		dir = newDir;
		syncFile(data);
		settings.set(0, newDir);
		saveSettings();
		storageLog.log(Level.INFO, "File directory set to : " +dir);
	}
	
	//Private method to update values in settings.txt
	private static void saveSettings() throws IOException{
		File f = new File(System.getProperty("user.dir").concat(File.separator)+"settings.txt");	
		FileWriter writer = new FileWriter(f,false);
		for(int i =0;i<settings.size();i++){
			writer.write(settings.get(i) + System.getProperty( "line.separator" ));
		}
		writer.close();
		storageLog.log(Level.INFO,"Settings saved.");
	}
	
	//API method to add new tasks into text file 
	public static void writeToFile(String Task) throws IOException{
		
		data.add(Task);
		tempAdd.add(Task);
		addToFile(Task);
		storageLog.log(Level.INFO,Task + " is added.");
	}
	
	//Internal method to add a single task into the text file (added to the bottom of the text file)
	private static void addToFile(String newTask) throws IOException{
		
		File f = new File(dir+outputName); 
		FileWriter writer = new FileWriter(f,true);	
		writer.write(newTask + System.getProperty( "line.separator" ));
		writer.close();
	}
	
	//API method to undo add command
	public static void undoAdd() throws IOException{
		data.remove(tempAdd.get(tempAdd.size()-1));
		tempAdd.remove(tempAdd.size()-1);
		syncFile(data);
	}
	
	//API method to fetch all data from text file into "data" arrayList
	public static ArrayList<String> getFromFile() throws IOException{
		initialise();
		if(fileExists(dir+outputName)){
			if(data.size()>0)
				data.clear();
			fetchFromFile(dir+outputName,data);
			storageLog.log(Level.INFO,"Data fetched");
			return data;
			
			
		}
		else {
			storageLog.log(Level.INFO,"No data available.");
			return new ArrayList<String>();
			
		}
	}
	
	//Private method to retrieve dir and outputName from settings.txt and create a new settings.txt if it does not exist
	private static void initialise() throws IOException{
		if(fileExists("settings.txt")){
			//Fetching properties
			settings= fetchFromFile(System.getProperty("user.dir").concat(File.separator+"settings.txt"),settings);
			dir = settings.get(0);
			outputName = settings.get(1);
			storageLog.log(Level.INFO,"Settings.txt loaded");
		}
		else {
			//First time running program, no properties set
			dir = System.getProperty("user.dir").concat(File.separator);//Getting root directory
			outputName = "TDNext.txt";//Default name
			settings.add(dir);
			settings.add(outputName);
			saveSettings();
			storageLog.log(Level.INFO,"Settings.txt created");
		}
		
	}
	
	//Internal method to fetch data from file, store into arrayList and return this arrayList
	private static ArrayList<String> fetchFromFile(String filePath,ArrayList<String> list) throws IOException{
			
		File f = new File(filePath); 
		FileReader reader = new FileReader(f);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line;
		//Scanning all lines from the text file into arrayList
		while ((line = bufferedReader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
		return list;
	}
	
	//API method to update tasks, either change value or mark as done
	public static void editToFile(String newVal, String orig) throws IOException{
		int index = findIndex(data,orig);
		updateTextFile(data,index,newVal);
		storageLog.log(Level.INFO,orig + " updated to : " + newVal);
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
		File f =  new File(dir+outputName);
		PrintWriter writer = new PrintWriter(f);
		writer.print("");
		writer.close();
		storageLog.log(Level.INFO,"All data cleared");
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
	public static void deleteFromFile(String task) throws IOException{
		data.remove(task);
		tempDel.add(task);
		syncFile(data);
		storageLog.log(Level.INFO,task + " deleted.");
	}
	
	//API method to undo delete command
	public static void undoDelete() throws IOException{
		data.add(tempDel.get(tempDel.size()-1));
		tempDel.remove(tempDel.size()-1);
		syncFile(data);
	}
	//Internal method to replace the entire text file according to the "data" arrayList
	private static void syncFile(ArrayList<String> list) throws IOException{
		File f = new File(dir+outputName);	
		FileWriter writer = new FileWriter(f,false);
		for(int i =0;i<list.size();i++){
			writer.write(list.get(i) + System.getProperty( "line.separator" ));
		}
		writer.close();
	}
	
	
	
	//Internal method to check if file already exists, and create a new one if it doesn't exist yet
	private static boolean fileExists(String filePath){
		
		File f = new File(filePath);
		
		if(f.exists()){
			storageLog.log(Level.INFO,filePath + " exists.");
			return true;
		}
		else {
			storageLog.log(Level.INFO,filePath + " does not exist.");
			return false;
		}
	}
	
	
}	

