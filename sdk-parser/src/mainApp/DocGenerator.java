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
		new DocGenerator().run("/usr/local/litle-home/vchouhan/Desktop/XML_Ref_elements.xml","/usr/local/litle-home/vchouhan/Desktop/testarena/testarena1");
	}
	
	public void run(String fileaddress, String dirAddress){
		
		try{
			ReadXMLFile rd = new ReadXMLFile();
			rd.extractDIVs(fileaddress);
			
			
			// got element list extracted from xml file
			List<DIVElement> eList = rd.getDIVs();
			Map<String, Object> noChangeMap = new HashMap<String, Object>();
			for(DIVElement first : eList){	
				if(!first.getEleName().trim().isEmpty()){
					first.generateElementDocForJava(dirAddress);
					for(Attribute a : first.getSubElements()){
						a.generateAttributesDocForJava(first, dirAddress);
						if(a.getNChanges() == 0){
							//noChangeElementList.add("No locations for the attribute : " + a.getName() + " of element : " + first.getEleName());
							if(!noChangeMap.containsKey(a.getName().toLowerCase()))
								noChangeMap.put(a.getName().toLowerCase(), a);
						}else{
							if(noChangeMap.containsKey(a.getName().toLowerCase()))
								noChangeMap.remove(a.getName().toLowerCase());
						}
					}
					if(first.getNChanges() == 0){
						if(!noChangeMap.containsKey(first.getEleName().toLowerCase()))
							noChangeMap.put(first.getEleName().toLowerCase(), first);
					}else{
						if(noChangeMap.containsKey(first.getEleName().toLowerCase()))
							noChangeMap.remove(first.getEleName().toLowerCase());
					}
					first.generateEnumDocForJava(dirAddress);
				}
			}
			for(Entry<String, Object> e : noChangeMap.entrySet()){
				//System.out.println(e.getKey());
				
				FileLocater ftest = new FileLocater();
				ftest.locate("", dirAddress);
				for(String fileAdd : ftest.getResult()){
					StringLocaterForJava stest = new StringLocaterForJava(fileAdd);
					stest.findLocations(e.getKey().toLowerCase());
					if(!stest.getLocations().isEmpty()){
						//this.setNChanges(this.getNChanges() + sl.getLocations().size());
						System.out.println("Element : " + e.getKey() + " updated comments at : " + stest.getLocations().size() + "for file : " + fileAdd);
						new ContentCombiner(stest.getLocations()).removeUnchangedDocs(new File(fileAdd));
					}
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
