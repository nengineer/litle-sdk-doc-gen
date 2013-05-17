package mainApp;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import combiner.ContentCombiner;

import Locaters.FileLocater;
//import Locaters.StringLocater;
import Locaters.StringLocaterForJava;

import extracter.DataExtracterForJava;
import extractXML.DIVElement;
import extractXML.ReadXMLFile;

import extracter.Employee;

public class DocGenerator {

	public static void main(String[] args){
		new DocGenerator().run("/usr/local/litle-home/vchouhan/Desktop/XML_Ref_elements.xml","/usr/local/litle-home/vchouhan/Desktop/MySpace/testonme");
	}
	
	public void run(String fileaddress, String dirAddress){
		
		try{
			File fXmlFile = new File(fileaddress);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			ReadXMLFile rd = new ReadXMLFile();
			rd.extractDIVs(doc);
			
			
			// got element list extracted from xml file
			List<DIVElement> eList = rd.getDIVs();
			
			DIVElement first  = new DIVElement();
			
			first = eList.get(0);
			
			// data extracted from DIV element
			DataExtracterForJava dx = new DataExtracterForJava();
			dx.extractData(first);
			dx.createData();
			String payLoad = dx.getData();
			
			// finding parent and appending commments now
			for(String parent : first.getParentElements()){
				FileLocater fl = new FileLocater();
				fl.locate(parent, dirAddress);
				if(!(fl.getResult() == null)){	
					for(String fileAdd : fl.getResult()){
						if(fileAdd.contains(".java")){
							StringLocaterForJava sl = new StringLocaterForJava(fileAdd);
							sl.findLocations(first.getEleName().toLowerCase());
							if(!sl.getLocations().isEmpty()){
								System.out.println("updated comments at : " + sl.getLocations().size() + "for file : " + fileAdd);
								new ContentCombiner(sl.getLocations()).combine(new File(fileAdd), payLoad);
							}
						}
					}	
				}	
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
}
