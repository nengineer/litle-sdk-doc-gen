package Locaters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class StringLocaterForJava implements StringLocater {
	
	List<Integer> locations = new ArrayList<Integer>();
	String fileAddress;
	//String keyword;
	
	public StringLocaterForJava(String add){
		this.fileAddress = add;
		//this.keyword = key;
	}
	
	public void findLocations(String key){
		
		StringLocaterForJava sTemp = this;
		
		File temp = new File(sTemp.getFileAddress());
		//checking for permissions
		if(temp.canRead()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(temp));
				int lineNum = 0;
				String currentLine;
				while((currentLine = reader.readLine()) != null){
					lineNum++;
					if(currentLine.equals(key)){
						sTemp.getLocations().add(lineNum);
					} 
				}
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			

		}else{
			return;
		}
		//return 0;
	}



	public List<Integer> getLocations() {
		// TODO Auto-generated method stub
		return locations;
	}

	public String getFileAddress() {
		// TODO Auto-generated method stub
		return fileAddress;
	}
	
	
	
}
