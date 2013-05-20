package mainApp;

import java.io.File;
import java.util.List;
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
			File fXmlFile = new File(fileaddress);
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			ReadXMLFile rd = new ReadXMLFile();
			rd.extractDIVs(doc);
			
			
			// got element list extracted from xml file
			List<DIVElement> eList = rd.getDIVs();
			
			for(DIVElement first : eList){	
				if(!first.getEleName().trim().isEmpty()){
					// data extracted from DIV element
					DataExtracterForJava dx = new DataExtracterForJava();
					dx.extractData(first);
					dx.createData();
					String payLoad = dx.getData();
					
					// finding elements for the corresponding attributes and updating
					for(Attribute a : first.getSubElements()){
						
						//System.out.println("Attribute : " + a.getName() + "is in the loop");
						
						DataExtracterForJava da = new DataExtracterForJava();
						da.extractDataForAttr(a);
						da.createData();
						String Attrdata = da.getData();
						
						FileLocater fattr = new FileLocater();
						
						fattr.locate(first.getEleName().toLowerCase(), dirAddress);
						if(!(fattr.getResult() == null)){	
							for(String fileAddattr : fattr.getResult()){
								if(fileAddattr.contains(".java")){
									StringLocaterForJava sattr = new StringLocaterForJava(fileAddattr);
									sattr.findLocations(a.getName().toLowerCase());
									//System.out.println(fileAdd);
									if(!sattr.getLocations().isEmpty()){
										System.out.println("Attribute : " + a.getName() + " updated comments at : " + sattr.getLocations().size() + "for file : " + fileAddattr);
										new ContentCombiner(sattr.getLocations()).combine(new File(fileAddattr), Attrdata);
									}
								}
							}	
						}
					}
					
					// finding parent and appending commments now
					for(String parent : first.getParentElements()){
						FileLocater fl = new FileLocater();
						fl.locate(parent, dirAddress);
						if(!(fl.getResult() == null)){	
							for(String fileAdd : fl.getResult()){
								if(fileAdd.contains(".java")){
									StringLocaterForJava sl = new StringLocaterForJava(fileAdd);
									sl.findLocations(first.getEleName().toLowerCase());
									//System.out.println(fileAdd);
									if(!sl.getLocations().isEmpty()){
										System.out.println("Element : " + first.getEleName() + " updated comments at : " + sl.getLocations().size() + "for file : " + fileAdd);
										new ContentCombiner(sl.getLocations()).combine(new File(fileAdd), payLoad);
									}
								}
							}	
						}	
					}
					// finding enumeration file types and appending enumeration over it
					for(Entry<String, String> e : first.getEnumerations().entrySet()){
						FileLocater fenum = new FileLocater();
						
						fenum.locate("typeenum", dirAddress);
						if(!(fenum.getResult() == null)){	
							for(String fileAddenum : fenum.getResult()){
								if(fileAddenum.contains(".java")){
									StringLocaterForJava senum = new StringLocaterForJava(fileAddenum);
									senum.findLocationsForEnum(e.getKey());
									
									DataExtracterForJava de = new DataExtracterForJava();
									
									de.extractDataForEnum(e.getValue());
									de.createData();
									String enumData = de.getData();
									//System.out.println(fileAdd);
									if(!senum.getLocations().isEmpty()){
										System.out.println("Enumeration : " + e.getKey() + " updated comments at : " + senum.getLocations().size() + "for file : " + fileAddenum);
										new ContentCombiner(senum.getLocations()).combine(new File(fileAddenum), enumData);
									}
								}
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
