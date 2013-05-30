package combiner;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ContentCombiner {

	private List<Integer> locations = new ArrayList<Integer>();
	private List<String> dataList; 
	private List<Integer> flaggedlocations;
	private LineMarker lineMarker;
	
	
	public ContentCombiner(List<Integer> loclist, LineMarker lm){
			
		this.locations = loclist;
		this.dataList =new ArrayList<String>();
		this.flaggedlocations = new ArrayList<Integer>();
		this.lineMarker = lm;
	}
	
	
    
    
    public void combine(File file, String data) {
    	try{
    		storeContent(file);
			processContent();
			appendContentAt(file,data);  		
			//cc.showContent(file);
    	}catch(Exception e){
    		e.printStackTrace();
    	} 
    	
    }
    
    
    public void storeContent(File file) {
    	try{
        	FileReader filereader = new FileReader(file);
    		BufferedReader reader = new BufferedReader(filereader);
    		String currentLine;
    		while((currentLine = reader.readLine()) !=null){
    			//String line = reader.readLine();
    			getDataList().add(currentLine);
    		}
    		reader.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	
    }
    
    public void appendContentAt(File file, String data) {
    	try{
        	int count = 0;
        	
        	FileWriter filewriter = new FileWriter(file);
    	    BufferedWriter bufferwriter = new BufferedWriter(filewriter);
        	while(count < getDataList().size()){
        		if(getFlaggedLocations().contains(count)){
        			count++;
        		}else{
               		bufferwriter.write(getDataList().get(count)+ "\n");
            		count++;
        		}
        		if(getLocations().contains(count+1)){
                	String payload = insertMargin(data,getMargin(getDataList().get(count)));
                	bufferwriter.write(payload+ "\n");
        		}
        	}
        	bufferwriter.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
    
    public void appendContent(File file, Map<Integer, List<String>> appendmap){
        try{
            int count = 0;
            
            FileWriter filewriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(filewriter);
            while(count < getDataList().size()){
                if(appendmap.containsKey(count)){
                    String payload = "";
                    for(String s : appendmap.get(count)){
                        payload = payload + s + "\n";
                    }
                    payload = insertMargin(payload,getMargin(getDataList().get(count)));
                    writer.write(payload + "\n");
                }
                if(getFlaggedLocations().contains(count)){
                    count++;
                }else{
                    writer.write(getDataList().get(count)+ "\n");
                    count++;
                }
            }
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        
        
    }
    
    public List<Integer> getLocations() {
		// TODO Auto-generated method stub
		return locations;
	}


	public void showContent(File file) {
    	try{
    		FileReader filereader = new FileReader(file);
    		BufferedReader reader = new BufferedReader(filereader);
    		String currentLine;
    		while((currentLine = reader.readLine()) !=null){
    			//String line = reader.readLine();
    			System.out.println(currentLine);
    		}
    		reader.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }
    
    
    public String getMargin(String st){
    	String result = "";
    	for(int i =0 ;i < st.length();i++){
    		if(!(st.charAt(i) == '\t' || st.charAt(i) == ' ')){
    			return result;
    		}
    		result = result + st.charAt(i);
		}
		return result;
    }
    
    public String insertMargin(String payLoad, String margin){
    	String[] loadparts = payLoad.split("\n");
    	String result = "";
    	for(int i = 0;i<loadparts.length;i++){
    		if(i==0){
    			result = result + margin + loadparts[i];
    		}else{
    			result = result + "\n" + margin + loadparts[i];
    		}
    	}
    	return result;
    }
    
    public void processContent(){
        for(int location : getLocations()){
            markLines(location-1);
        }
    }
    
	public void markLines(int location){
		this.lineMarker.markLines(location, this);
	}
	
	public void removeUnchangedDocs(File file){
		
		storeContent(file);
		processContent();
		removeFlagged(file);
	}
	
	public void removeFlagged(File file){
    	try{
        	int count = 0;
        	FileWriter filewriter = new FileWriter(file);
    	    BufferedWriter bufferwriter = new BufferedWriter(filewriter);
        	while(count < getDataList().size()){
        		if(getFlaggedLocations().contains(count)){
        			count++;
        		}else{
               		bufferwriter.write(getDataList().get(count)+ "\n");
            		count++;
        		}
        	}
        	bufferwriter.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
    
        
    public List<String> getDataList(){
    	return this.dataList;
    }
    
    public List<Integer> getFlaggedLocations(){
    	return this.flaggedlocations;
    }
    
    public void addToFlaggedLocation(int loc){
    	this.flaggedlocations.add(loc);
    }
    
}
