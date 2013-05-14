package extractXML;
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DIVElement {
	
	private String eleName;
	private String eleType;
	private ArrayList<String> parentElements;
	private ArrayList<String> childElements;
	//private ArrayList<Attribute> attributes;
	
	public DIVElement(){
		this.eleName = "";
		this.parentElements = new ArrayList<String>();
		this.childElements = new ArrayList<String>();
	}
	
	public String getEleName() {
		return eleName;
	}

	public void setEleName(String eleName) {
		this.eleName = eleName;
	}

	public String getEleType() {
		return eleType;
	}

	public void setEleType(String eleType) {
		this.eleType = eleType;
	}

	public ArrayList<String> getParentElements() {
		return parentElements;
	}
	
	public void setParentElements(ArrayList<String> parentElements) {
		this.parentElements = parentElements;
	}
	
	public void addEleToParentElements(String e){
		this.parentElements.add(e);
	}

	public ArrayList<String> getChildElements() {
		return childElements;
	}

	public void setChildElements(ArrayList<String> childElements) {
		this.childElements = childElements;
	}

	
	
	public void processDIV(Element div){
		this.extractItemName(div);
		System.out.println("Item Name: " + this.eleName);
		this.findParentElement(div);
		for(String s: parentElements){
			System.out.println("Parent Element: "+s);
		}
	}
	
	public void extractItemName(Element div){
		NodeList nodes = div.getElementsByTagName(XMLLookUpStrings.H1_HEAD1);  
		for(int i=0; i< nodes.getLength(); i++){
				Node eleA = nodes.item(i);
				if(eleA.getNodeType() == Node.ELEMENT_NODE){
					String itemName = trimString(eleA.getTextContent());
					if(itemName != ""){
						this.setEleName(itemName);
					}
				}
		}
	}
	
	public void findParentElement(Element div){
		NodeList nodes = div.getElementsByTagName(XMLLookUpStrings.SH1_SUBHEAD1);
		for (int i=0; i < nodes.getLength(); i++){
			Node node = nodes.item(i);
			if(trimString(node.getTextContent()).equals(XMLLookUpStrings.PARENTELEMENTS)){
				Node sibling = node.getNextSibling();
				while(sibling != null) {
				    if ( sibling.getNodeType() == Node.ELEMENT_NODE ) {
				    	Element parentE = (Element)sibling;
				    	extractParentElement(parentE);
				    	break;
				    }

				    sibling = sibling.getNextSibling();
				}

			}
		}
	}
	
	public void extractParentElement(Element pe){
		NodeList nodes = pe.getElementsByTagName(XMLLookUpStrings.COLOR);
		for (int i=0; i<nodes.getLength(); i++){
			Node node = nodes.item(i);
			String itemName = trimString(node.getTextContent());
			if(itemName != ""){
				this.addEleToParentElements(itemName);
			}
		}
	}
	
	private String trimString(String raw){
		return raw.replaceAll("\\s","");
	}

	
	

}
