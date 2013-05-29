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

	List<Integer> locations = new ArrayList<Integer>();
	List<String> dataList; 
	List<Integer> flaggedlocations;
	LineMarker lineMarker;
	
	
	public ContentCombiner(List<Integer> loclist, LineMarker lm){
			
		this.locations = loclist;
		this.dataList =new ArrayList<String>();
		this.flaggedlocations = new ArrayList<Integer>();
		this.lineMarker = lm;
	}
	
	
    public static void main( String[] args )
    {	
    	try{
    		
    		//String data = "This new content will append to the middle of the file\n";
    		String data1 = "catch me if you can";
    		
    		File file =new File("/usr/local/litle-home/vchouhan/Desktop/project/test1/text.txt");
    		
    		
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
    		
    		List<Integer> tl = new ArrayList<Integer>();
    		LineMarker lm = new LineMarkerForRuby();
    		
    		tl.add(4);
    		tl.add(6);
    		
    		//new ContentCombiner(tl, lm).combine(file, data1);
    		
    		List<String> list1 = new ArrayList<String>();
    		
    		list1.add("#updated_first string added");
    		
    		list1.add("#updated_second string added");
    		
    		List<String> list2 = new ArrayList<String>();
            
            list2.add("#updated_third string added");
            
            list2.add("#updated_fourth string added");
    		
    		Map<Integer, List<String>> testmap = new HashMap<Integer, List<String>>();
    		
    		testmap.put(3, list1);
    		
    		testmap.put(5, list2);
    		ContentCombiner cr = new ContentCombiner(tl,lm);
    		cr.removeUnchangedDocs(file);
    		
    		for(int i : cr.getFlaggedLocations()){
    		    System.out.println(i);
    		}
    		
    		ContentCombiner cnew = new ContentCombiner(tl, lm);
    		
    		cnew.storeContent(file);
     		
    		cnew.appendContent(file, testmap);
    		
    		
//    		i am first line
//    		i am second line
//    		i am third line
//    		i am fourth line
//    		i am fifth line
//    		i am sixth line
//    		i am seventh line
//    		i am eight line
//    		i am nine line
//    		i am tenth line
    		
    		
    		//new ContentCombiner(7).combine(file, data1); 
    
    	}catch(IOException e){
    		e.printStackTrace();
    	} 
    }
    
    
    
    public void combine(File file, String data) {
    	try{
    		ContentCombiner cc = this;
			cc.storeContent(file);
			cc.processContent();
			cc.appendContentAt(file,data);  		
			//cc.showContent(file);
    	}catch(Exception e){
    		e.printStackTrace();
    	} 
    	
    }
    
    
    public void storeContent(File file) {
    	try{
        	ContentCombiner cc = this;
        	FileReader filereader = new FileReader(file);
    		BufferedReader reader = new BufferedReader(filereader);
    		String currentLine;
    		while((currentLine = reader.readLine()) !=null){
    			//String line = reader.readLine();
    			cc.getDataList().add(currentLine);
    		}
    		reader.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	
    }
    
    public void appendContentAt(File file, String data) {
    	try{
        	ContentCombiner cc = this;
        	
        	int count = 0;
        	
        	FileWriter filewriter = new FileWriter(file);
    	    BufferedWriter bufferwriter = new BufferedWriter(filewriter);
        	while(count < cc.getDataList().size()){
        		if(cc.getFlaggedLocations().contains(count)){
        			count++;
        		}else{
               		bufferwriter.write(cc.getDataList().get(count)+ "\n");
            		count++;
        		}
        		if(cc.getLocations().contains(count+1)){
                	String payload = cc.insertMargin(data,cc.getMargin(cc.getDataList().get(count)));
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
                writer.write(getDataList().get(count) + "\n");
                count++;
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
        if(!this.getLocations().isEmpty()){
            for(int location : getLocations()){
                markLines(location-1);
            }
        }else{
            markLines(0);
        }

    }
    
	public void markLines(int location){
		
//		List<String> dlist = this.getDataList();
//		int i = location-1;		
//		while(dlist.get(i).trim().isEmpty()){
//			i--;
//		}
//		if(dlist.get(i).trim().endsWith("*/")){
//			while(!dlist.get(i).trim().contains("/*")){
//				getFlaggedLocations().add(i);
//				i--;
//			}
//			getFlaggedLocations().add(i);
//		}
		this.lineMarker.markLines(location, this);
	}
	
	public void removeUnchangedDocs(File file){
		ContentCombiner cc = this;
		
		cc.storeContent(file);
		cc.processContent();
		cc.removeFlagged(file);
	}
	
	public void removeFlagged(File file){
    	try{
        	ContentCombiner cc = this;
        	int count = 0;
        	FileWriter filewriter = new FileWriter(file);
    	    BufferedWriter bufferwriter = new BufferedWriter(filewriter);
        	while(count < cc.getDataList().size()){
        		if(cc.getFlaggedLocations().contains(count)){
        			count++;
        		}else{
               		bufferwriter.write(cc.getDataList().get(count)+ "\n");
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
