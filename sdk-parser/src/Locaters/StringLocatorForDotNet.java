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
						sTemp.addToLocations(lineNum);
					}
				}
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void findLocationsForEnum(String key, ArrayList<String> keys){

		StringLocatorForDotNet sTemp = this;

		File temp = new File(sTemp.getFileAddress());
		//checking for permissions
		if(temp.canRead()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(temp));
				String currentLine;
				boolean lock = false;
				int lineNum = 0;
				int startLine = -1, endLine = -1;
				String block = "";
				while((currentLine = reader.readLine()) != null){
				    lineNum ++;

					Pattern p = Pattern.compile("public enum .*");
					Matcher m = p.matcher(currentLine.toLowerCase());
					Pattern p1 = Pattern.compile("}");
					Matcher m1 = p1.matcher(currentLine.toLowerCase());
					if( m.find() && !lock){
					    startLine = lineNum;
						lock = true;
					}
					else if(m1.find() && lock){

		                if(containsAll(block, keys)){
		                    endLine = lineNum;
	                        break;
		                }
		                else{
		                    startLine = -1;
		                    endLine = -1;
		                    block = "";
		                }
		                lock= false;

					}
					if(lock){
					    block += currentLine;
					}
				}
				reader.close();
    			reader = new BufferedReader(new FileReader(temp));
    			for(lineNum = 1; lineNum < startLine; lineNum++){
    			    reader.readLine();
    			}
    			for(lineNum = startLine; lineNum < endLine; lineNum++){
    			    currentLine = reader.readLine();
                    Pattern p = Pattern.compile("\\b"+key.toLowerCase().replace("\\s","")+"\\b"+",");
                    Matcher m = p.matcher(currentLine.toLowerCase());
                    if(m.find()){
                        sTemp.addToLocations(lineNum);
                    }
    			}
    			reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public boolean containsAll(String block, ArrayList<String> keys){
	    for(String key: keys){
	        if(!block.contains(key)){
	            return false;
	        }
	    }
	    return true;
	}

	public List<Integer> getLocations() {
		// TODO Auto-generated method stub
		return locations;
	}

	public void addToLocations(int loc){
	    locations.add(loc);
	}

	public String getFileAddress() {
		// TODO Auto-generated method stub
		return fileAddress;
	}



}
