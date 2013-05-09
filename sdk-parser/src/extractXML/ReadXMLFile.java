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
			NodeList nodes = doc.getElementsByTagName("DIV");
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
 
	File fXmlFile = new File("/usr/local/litle-home/zhe/parsePDF/xml_XML_Ref_elements.xml");
	
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
 
	doc.getDocumentElement().normalize();
	
	ReadXMLFile x = new ReadXMLFile();
 
	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	
	x.extractDIVs(doc);
 
//	
//	Node ele0 = doc.getElementsByTagName("A").item(0);
//	String parentEle = ele0.getTextContent();
//	if(parentEle.equals("Parent Elements")){
//		
//	}
// 
//	System.out.println("\n");
// 
//	for (int temp = 0; temp < nList.getLength(); temp++) {
// 
//		Node nNode = nList.item(temp);
// 
//		System.out.println("\nCurrent Element :" + nNode.getNodeName());
// 
//		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
// 
//			Element eElement = (Element) nNode;
// 
//			System.out.println("Staff id : " + eElement.getAttribute("id"));
//			System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
//			System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
//			System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
//			System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
// 
//			}
//	}
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
 
}
