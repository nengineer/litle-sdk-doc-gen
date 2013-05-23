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

import Locaters.FileLocater;
//import Locaters.StringLocater;
import Locaters.StringLocaterForJava;

import extracter.DataExtracterForJava;
import extractXML.Attribute;
import extractXML.DIVElement;
import extractXML.ReadXMLFile;

import extracter.Employee;

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

			// created a map to gather unused elements and attributes
			Map<String, Integer> noChangeMap = new HashMap<String, Integer>();
			
			for(DIVElement first : eList){	
				if(!first.getEleName().trim().isEmpty()){
					first.generateElementDocForJava(dirAddress);
					
					// processing attribute for the Element
					for(Attribute a : first.getSubElements()){
						a.generateAttributesDocForJava(first, dirAddress);
						
						// adding attribute to the Map for processing left out java docs 
						if(a.getNChanges() == 0 && !noChangeMap.containsKey(a.getName().toLowerCase()))
								noChangeMap.put(a.getName().toLowerCase(), 0);
						else if(a.getNChanges() > 0 && noChangeMap.containsKey(a.getName().toLowerCase()))
								noChangeMap.remove(a.getName().toLowerCase());
					}
					
					// adding Element to the map for processing left out java docs 
					if(first.getNChanges() == 0 && !noChangeMap.containsKey(first.getEleName().toLowerCase()))
							noChangeMap.put(first.getEleName().toLowerCase(), 0);
					else if(first.getNChanges() > 0 && noChangeMap.containsKey(first.getEleName().toLowerCase()))
							noChangeMap.remove(first.getEleName().toLowerCase());

					// processing Enumeration of the Element
					first.generateEnumDocForJava(dirAddress);
				}
			}
			
			
			FileLocater ftest = new FileLocater();
			
			ftest.locate("java", dirAddress);
			
			for(String fileAdd : ftest.getResult()){
				if(new File(fileAdd).canWrite()){
					System.out.println("processing file : " + fileAdd);
					ContentCombiner ctest = new ContentCombiner(new ArrayList<Integer>());
					ctest.storeContent(new File(fileAdd));
					for(String cline : ctest.getDataList()){
						if(cline.trim().startsWith("public")){
							for(Entry<String, Integer> e : noChangeMap.entrySet()){
								if(cline.toLowerCase().contains("set" + e.getKey().toLowerCase() + "(")
									||cline.toLowerCase().contains("get" + e.getKey().toLowerCase() + "(")
									||cline.toLowerCase().contains("is" + e.getKey().toLowerCase() + "(")){
										ctest.getLocations().add(ctest.getDataList().indexOf(cline)+1);
										e.setValue(e.getValue() + 1);
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

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
