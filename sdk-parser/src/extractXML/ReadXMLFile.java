package extractXML;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
public class ReadXMLFile {
	
	private ArrayList<DIVElement> DIVlist = new ArrayList<DIVElement>();
	
	public ReadXMLFile(){
		
	};
	
	public void extractDIVs(Document doc){
		
		if(doc != null){
			NodeList nodes = doc.getElementsByTagName(XMLLookUpStrings.DIV);
			for(int i=0; i<nodes.getLength(); i++){
				Node node = nodes.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element e = (Element) node;
					DIVElement div = new DIVElement();
					div.processDIV(e);
					//DIVlist.add(div);
				}

			}
		}
		
	}
	
	public ArrayList<DIVElement> getDIVs(){
		return DIVlist;
	}
	
 
	public static void main(String argv[]) {
 
    try {
 
	File fXmlFile = new File(argv[0]);
	
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
 
	doc.getDocumentElement().normalize();
	
	ReadXMLFile extractor = new ReadXMLFile();
 
	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	
	extractor.extractDIVs(doc);

    } catch (Exception e) {
	e.printStackTrace();
    }
  }
 
}
