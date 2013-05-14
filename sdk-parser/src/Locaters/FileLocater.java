package Locaters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;


public class FileLocater {
	
	private String fileNameToSearch;
	private List<String> result = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		FileLocater fl = new FileLocater();
		fl.locate("search.txt","/usr/local/litle-home/vchouhan/Desktop/testarena");
		
		List<StringLocaterForJava> sl = new ArrayList<StringLocaterForJava>();
		for(String address : fl.getResult()){
			sl.add(new StringLocaterForJava(address));
		}
		
		for(StringLocaterForJava s : sl){
			s.findLocations("these are our employee details");
			System.out.println(s.getLocations().size());
		}
	}
	  
	public void locate(String fname, String dirAddress){
		FileLocater fileSearch = this;
			 
		fileSearch.searchDirectory(new File(dirAddress), fname);
		 
		//int count = fileSearch.getResult().size();
		
		/**
		if(count ==0){
			System.out.println("\nNo result found!");
		}else{
			//System.out.println("\nFound " + count + " result!\n");
			for (String matched : fileSearch.getResult()){
				int i = (new FileLocater()).locateString(matched,"these are our employee details");
				if(i != 0){
					
					System.out.println("Found : " + matched + "...and got the string // at line Number : " + i);
				}else{
					System.out.println("Found : " + matched + "...however no string match...");
				}
			}
		}**/
	}
	 
	public void searchDirectory(File directory, String fileNameToSearch) {
	 
		setFileNameToSearch(fileNameToSearch);
	 
		if (directory.isDirectory()) {
		    search(directory);
		} else {
		    System.out.println(directory.getAbsoluteFile() + " is not a directory!");
		}
	 
	}
	 
	private void search(File file) {
	 
		if (file.isDirectory()) {
			//System.out.println("Searching directory ... " + file.getAbsoluteFile());
	 
			//checking for permissions	
			if (file.canRead()) {
			for (File temp : file.listFiles()) {
			    if (temp.isDirectory()) {
				search(temp);
			    } else {
			    	
			    	 
			    	if (getFileNameToSearch().equals(temp.getName())) {	
			    		//System.out.println("found match!!!");
			    		result.add(temp.getAbsoluteFile().toString());
			    	}else{
			    		//System.out.println("name mismatch....name of the file is : " + temp.getName());	
			    	}
			    	
			    	
	 
			    }
			}
	 
		 } else {
			 System.out.println(file.getAbsoluteFile() + "Permission Denied");
		 }
		}
	 
	}

	public String getFileNameToSearch() {
		return fileNameToSearch;
	}
	 
	public void setFileNameToSearch(String fileNameToSearch) {
		this.fileNameToSearch = fileNameToSearch;
	}
	 
	public List<String> getResult() {
		return result;
	}

	public int locateString(String address, String field){
	  	
		File temp = new File(address);
		//checking for permissions
		if(temp.canRead()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(temp));
				int lineNum = 0;
				String currentLine;
				while((currentLine = reader.readLine()) != null){
					lineNum++;
					if(currentLine.equals(field)){
						System.out.println("found the string...");
						//appendTemp();
						//return lineNum;
					} //else{
						//System.out.println("string not in the file");
						//System.out.println(currentLine);
						//return lineNum;
					//}
				}
				reader.close();
				return lineNum;
			}catch(Exception e){
				e.printStackTrace();
			}
			

		}else{
			System.out.println("permission denied!!!");
			return 0;
		}
		return 0;
	}
}
