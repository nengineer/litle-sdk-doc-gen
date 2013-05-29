package mainApp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import combiner.ContentCombiner;
import combiner.LineMarkerForJava;

import Locaters.FileLocater;
//import Locaters.StringLocater;
import Locaters.StringLocaterForJava;

import extracter.DataExtracterForJava;
import extractXML.Attribute;
import extractXML.DIVElement;
import extractXML.ReadXMLFile;

public class DocGenerator {

	public static void main(String[] args){
		new DocGenerator().run("/usr/local/litle-home/vchouhan/Desktop/XML_Ref_elements.xml","/usr/local/litle-home/vchouhan/Desktop/testarena/testarena1/testarena2/litle-sdk-for-java");
	}
	
	public void run(String fileaddress, String dirAddress){
		
		try{
			ReadXMLFile rd = new ReadXMLFile();
			rd.extractDIVs(fileaddress);
			
			
			// got element list extracted from XML file
			List<DIVElement> eList = rd.getDIVs();

			// cleaning the old auto generated comments from the java package 
			Map<String, Integer> noChangeMap = new HashMap<String, Integer>();
			
			for(DIVElement e : eList){
			    if(!e.getEleName().trim().isEmpty()){
			        if(!noChangeMap.containsKey(e.getEleName().toLowerCase()))
			            noChangeMap.put(e.getEleName().toLowerCase(), 0);
			        
			        for(Attribute a : e.getSubElements()){
		                 if(!noChangeMap.containsKey(a.getName().toLowerCase()))
		                        noChangeMap.put(a.getName().toLowerCase(), 0);
			        }
			    }
			}
			
	         FileLocater ftest = new FileLocater();
	            
	         ftest.locate("java", dirAddress + "/generated/com/litle/sdk/generate/");
	            
	         for(String fileAdd : ftest.getResult()){
	             if(new File(fileAdd).canWrite()){
	                 System.out.println("processing file : " + fileAdd);
	                 ContentCombiner ctest = new ContentCombiner(new ArrayList<Integer>(), new LineMarkerForJava());
	                 ctest.storeContent(new File(fileAdd));
	                 for(String cline : ctest.getDataList()){
	                     if(cline.trim().startsWith("public")){
	                         for(Entry<String, Integer> e : noChangeMap.entrySet()){
	                             if(cline.toLowerCase().contains("set" + e.getKey().toLowerCase() + "(")
	                                     ||cline.toLowerCase().contains("get" + e.getKey().toLowerCase() + "(")
	                                     ||cline.toLowerCase().contains("is" + e.getKey().toLowerCase() + "(")){
	                                 ctest.getLocations().add(ctest.getDataList().indexOf(cline)+1);
	                                 e.setValue(e.getValue() + ctest.getLocations().size());
	                             }
	                         }
	                     }
	                 }
	                 System.out.println(" at " + ctest.getLocations().size() + " number of places");
	                 ctest.processContent();
	                 ctest.removeFlagged(new File(fileAdd));
	             }
	         }
	            
	         for(Entry<String, Integer> e : noChangeMap.entrySet()){
	             System.out.println(e.getKey() + " : " + e.getValue());
	         }
			
	         Map<String, Integer> noRepeatMap = new HashMap<String, Integer>();
			
	         for(DIVElement first : eList){	
	             if(!first.getEleName().trim().isEmpty()){
	                 first.generateElementDocForJava(dirAddress);
	                 noRepeatMap.put(first.getEleName().toLowerCase(), 0);
					// processing attribute for the Element
					for(Attribute a : first.getSubElements()){
					    if(!noRepeatMap.containsKey(a.getName().toLowerCase())){
					        a.generateAttributesDocForJava(first, dirAddress);
					        noRepeatMap.put(a.getName().toLowerCase(), 0);
					    }
					}
					// processing Enumeration of the Element
					first.generateEnumDocForJava(dirAddress);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
