package extractXML;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
 
public class ReadXMLFile {
	
	ReadXMLFile(){};
	
	public void extractDIVs(Document doc){
		
		if(doc != null){
			NodeList nodes = doc.getElementsByTagName(XMLLookUpStrings.DIV);
			for(int i=0; i<nodes.getLength(); i++){
				Node node = nodes.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element e = (Element) node;
					DIVElement div = new DIVElement();
					div.processDIV(e);
				}

			}
		}
		
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
