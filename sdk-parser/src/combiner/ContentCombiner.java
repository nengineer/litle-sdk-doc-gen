package combiner;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class ContentCombiner {

	List<Integer> locations = new ArrayList<Integer>();
	List<String> dataList; 
	
	
	public ContentCombiner(List<Integer> loclist){
			
		this.locations = loclist;
		this.dataList =new ArrayList<String>();
	}
	
	
    public static void main( String[] args )
    {	
    	try{
    		
    		//String data = "This new content will append to the middle of the file\n";
    		String data1 = "i m getting problems";
    		
    		File file =new File("/usr/local/litle-home/vchouhan/Desktop/project/test1/text.txt");
    		
    		
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
    		
    		List<Integer> tl = new ArrayList<Integer>();
    		
    		tl.add(5);
    		tl.add(10);
    		
    		new ContentCombiner(tl).combine(file, data1);
    		
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
			cc.showContent(file);
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
        		bufferwriter.write(cc.getDataList().get(count)+ "\n");
        		count++;
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
    	ContentCombiner cc = this;
    	for(int location : cc.getLocations()){
    		cc.removeLastComments(location);
    	}
    }
    
	public void removeLastComments(int last){
		List<String> stlist = this.getDataList();
		if(this.getDataList().get(last-1).trim().isEmpty()){
			last--;
			removeLastComments(last);
		}else if(this.getDataList().get(last-1).trim().endsWith("*/")){
			int j = last;
			while(!this.getDataList().get(j-1).trim().startsWith("/*")){
				this.getDataList().remove(j-1);
				for(int location : this.locations){
					if((location-1)>= j){
						System.out.println("old location modified" + location + "modified");
						location--;
						System.out.println("new location is : " + location);
					}
					
					System.out.println("old location unmodified");
				}
				j--;
			}
			stlist.remove(j-1);
			for(int location : locations){
				if((location-1)>= j){
					location--;
				}
			}
			return;
		}else{
			return;
		}
	}
    
    
        
    public List<String> getDataList(){
    	return this.dataList;
    }
    
}
