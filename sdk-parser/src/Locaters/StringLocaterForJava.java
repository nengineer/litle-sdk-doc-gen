package Locaters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
					
//					Pattern p = Pattern.compile("public .*"+key.toLowerCase()+"(.*)");
//					Matcher m = p.matcher(currentLine.toLowerCase());
//					if(m.find() && !currentLine.toLowerCase().contains("=")){
//						sTemp.getLocations().add(lineNum);
//					}
					
//					String[] currentLineArray = currentLine.split("^[(]");
//					
//					if(currentLineArray.length == 2){
//						if(currentLineArray[0].endsWith(key)){
//							sTemp.getLocations().add(lineNum);
//						}
//					}
					
					if(currentLine.toLowerCase().contains(key.toLowerCase()) && currentLine.contains("public")&& currentLine.contains("(") && !currentLine.contains("=")){
						
						String[] parts = currentLine.split("(");
						
						
						
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
