package extractXML;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXMLFile {

	private ArrayList<DIVElement> DIVlist = new ArrayList<DIVElement>();

	public ReadXMLFile(){

	};

	public void extractDIVs(String fileaddress) throws ParserConfigurationException, SAXException{

		  FileWriter fstream = null;
		try {

			File fXmlFile = new File(fileaddress);

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			//ReadXMLFile rd = new ReadXMLFile();

			fstream = new FileWriter("/usr/local/litle-home/zhe/parsePDF/parsedOutput.txt");

			BufferedWriter out = new BufferedWriter(fstream);

			if(doc != null){
				NodeList nodes = doc.getElementsByTagName(XMLLookUpStrings.DIV);
				for(int i=0; i<nodes.getLength(); i++){
					Node node = nodes.item(i);
					if(node.getNodeType() == Node.ELEMENT_NODE){
						Element e = (Element) node;
						DIVElement div = new DIVElement();
						div.processDIV(e, out);
						if(div.getEleName().contains(",")){
							String[] eleNames = div.getEleName().split(",");
							for(String eleName: eleNames){
							    DIVElement newDiv = new DIVElement();
							    newDiv.processDIV(e, null);
								newDiv.setEleName(eleName);
								DIVlist.add(newDiv);
							}
						}
						else{
							DIVlist.add(div);
						}
					}

				}
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	extractor.extractDIVs(argv[0]);

    } catch (Exception e) {
	e.printStackTrace();
    }
  }

}
