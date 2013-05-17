package Locaters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class FileLocater {
	
	private String fileNameToSearch;
	private List<String> result = new ArrayList<String>();
	
	  
	public void locate(String fname, String dirAddress){
		FileLocater fileSearch = this;	 
		fileSearch.searchDirectory(new File(dirAddress), fname);
	}
	 
	public void searchDirectory(File directory, String fileNameToSearch) {
	 
		setFileNameToSearch(fileNameToSearch);
		if (directory.isDirectory()) {
		    search(directory);
		} 
	}
	 
	private void search(File file) {
	 
		if (file.isDirectory()) {
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						search(temp);
					} else {
						if (temp.getName().toLowerCase().contains(getFileNameToSearch().toLowerCase())) {	
							result.add(temp.getAbsoluteFile().toString());
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

}
