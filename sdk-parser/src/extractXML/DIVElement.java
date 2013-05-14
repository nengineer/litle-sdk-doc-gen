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

	
	/**
	 * process the whole block of XML element
	 * @param div
	 */
	public void processDIV(Element div){
		this.extractItemName(div);
		System.out.println("Item Name: " + this.eleName);
		this.extractSubHeaderElement(div);
		for(String s: parentElements){
			System.out.println("Parent Element: "+s);
		}
	}
	
	/**
	 * extract the name of the XML element
	 * @param div
	 */
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
	
	/**
	 * extract subheader elements including parentElements, childElements, and attributes
	 * @param div
	 */
	public void extractSubHeaderElement(Element div){
		NodeList nodes = div.getElementsByTagName(XMLLookUpStrings.SH1_SUBHEAD1);
		for (int i=0; i < nodes.getLength(); i++){
			Node node = nodes.item(i);
			String nodeContent = trimString(node.getTextContent());
			if(nodeContent.equals(XMLLookUpStrings.PARENTELEMENTS)){
				extractParentElement(node);
			}
		}
	}
	
	
	public void extractParentElement(Node node){
		Node sibling = node.getNextSibling();
		while(sibling != null) {
		    if ( sibling.getNodeType() == Node.ELEMENT_NODE ) {
		    	Element pe = (Element)sibling;
				NodeList parents = pe.getElementsByTagName(XMLLookUpStrings.COLOR);
				for (int i=0; i<parents.getLength(); i++){
					Node parent = parents.item(i);
					String parentName = trimString(parent.getTextContent());
					if(parentName != ""){
						this.addEleToParentElements(parentName);
					}
				}
		    	break;
		    }

		    sibling = sibling.getNextSibling();
		}

	}
	
	private String trimString(String raw){
		return raw.replaceAll("\\s","");
	}

	
	

}
