package Locaters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;


public class FileLocater {
	
	private String fileNameToSearch;
	private String result;
	

	  
	public void locate(String fname, String dirAddress){
		FileLocater fileSearch = this;			 
		fileSearch.searchDirectory(new File(dirAddress), fname);		 
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
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						search(temp);
					} else {
						if (temp.getName().toLowerCase().contains(getFileNameToSearch().toLowerCase())){
							this.setResult(temp.getAbsoluteFile().toString());
							return;		
						}
					}
				}	 
			} else {
				System.out.println(file.getAbsoluteFile() + "Permission Denied");
			}
		}	 
	}

	private void setResult(String string) {
		// TODO Auto-generated method stub
		this.result = string;
	}

	public String getFileNameToSearch() {
		return fileNameToSearch;
	}
	 
	public void setFileNameToSearch(String fileNameToSearch) {
		this.fileNameToSearch = fileNameToSearch;
	}
	 
	public String getResult() {
		return result;
	}
}
