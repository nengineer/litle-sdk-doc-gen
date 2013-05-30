package Locaters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringLocaterForJava implements StringLocater {
	
	private List<Integer> locations = new ArrayList<Integer>();
	private String fileAddress;
	
	public StringLocaterForJava(String add){
		this.fileAddress = add;
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
					if(currentLine.toLowerCase().contains("set" + key + "(".toLowerCase()) && currentLine.contains("public")&&!currentLine.contains("=")){
						sTemp.getLocations().add(lineNum);						
					} else if(currentLine.toLowerCase().contains("get" + key + "(".toLowerCase()) && currentLine.contains("public")&&!currentLine.contains("=")){
						sTemp.getLocations().add(lineNum);	
					}else if(currentLine.toLowerCase().contains("is" + key + "(".toLowerCase()) && currentLine.contains("public")&&!currentLine.contains("=")){
						sTemp.getLocations().add(lineNum);
					}
				}
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void findLocationsForEnum(String key){
		
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
					if(currentLine.trim().matches(".*\\b("+key.toUpperCase()+")\\b.*")&&currentLine.trim().startsWith(key.toUpperCase())){
						sTemp.getLocations().add(lineNum);						
					} 
				}
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
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
