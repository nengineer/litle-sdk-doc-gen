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

	int lineNum;
	List<String> dataList; 
	
	
	public ContentCombiner(int ln){
		this.lineNum = (ln-1);
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
    		
    		new ContentCombiner(7).combine(file, data1);
    
    	}catch(IOException e){
    		e.printStackTrace();
    	} 
    }
    
    
    
    public void combine(File file, String data) {
    	try{
    		ContentCombiner cc = this;
			cc.storeContent(file);
			cc.appendContentAt(file,data);  		
		//	cc.showContent(file);
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
        	while(count < cc.getLineNum() && cc.getLineNum() < cc.getDataList().size()){
        		bufferwriter.write(cc.getDataList().get(count)+ "\n");
        		count++;
        	}
        	
        	bufferwriter.write(data+ "\n");
        	
        	while(count < cc.getDataList().size()){
        		bufferwriter.write(cc.getDataList().get(count)+ "\n");
        		count++;
        	}
        	bufferwriter.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}

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
    
    public int getLineNum(){
    	return this.lineNum;
    }
    
    public List<String> getDataList(){
    	return this.dataList;
    }
    
}
