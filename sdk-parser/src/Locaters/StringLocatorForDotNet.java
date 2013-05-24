package Locaters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringLocatorForDotNet implements StringLocater {

	List<Integer> locations = new ArrayList<Integer>();
	String fileAddress;
	//String keyword;

	public StringLocatorForDotNet(String add){
		this.fileAddress = add;
		//this.keyword = key;
	}

	@Override
    public void findLocations(String key){

		StringLocatorForDotNet sTemp = this;

		File temp = new File(sTemp.getFileAddress());
		//checking for permissions
		if(temp.canRead()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(temp));
				int lineNum = 0;
				String currentLine;
				while((currentLine = reader.readLine()) != null){
					lineNum++;

					Pattern p = Pattern.compile("public .*" + "\\b" + key + "\\b");
					Matcher m = p.matcher(currentLine.toLowerCase());
					if( m.find()){
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

		StringLocatorForDotNet sTemp = this;

		File temp = new File(sTemp.getFileAddress());
		//checking for permissions
		if(temp.canRead()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(temp));
				int lineNum = 0;
				String currentLine;
				boolean lock = false;
				while((currentLine = reader.readLine()) != null){
					lineNum++;

					Pattern p = Pattern.compile("public enum .*");
					Matcher m = p.matcher(currentLine.toLowerCase());
					Pattern p1 = Pattern.compile("\\b"+key+"\\b"+",");
					Matcher m1 = p1.matcher(currentLine.toLowerCase());
					if( m.find() && !lock){
						lock = true;
					}
					else if(m1.find() && lock){
						sTemp.getLocations().add(lineNum);
						lock = false;
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
