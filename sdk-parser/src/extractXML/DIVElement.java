package extractXML;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DIVElement {
	
	private String eleName;
	private String eleType;
	private ArrayList<String> parentElements;
	private HashMap<String, Integer> childElements; //1 for required, 0 for optional
	//private ArrayList<Attribute> attributes;
	
	public DIVElement(){
		this.eleName = "";
		this.parentElements = new ArrayList<String>();
		this.childElements = new HashMap<String, Integer>();
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

	public HashMap<String, Integer> getChildElements() {
		return childElements;
	}

	public void setChildElements(HashMap<String, Integer> childElements) {
		this.childElements = childElements;
	}
	
	public void addEleToChildElements(String e){
		this.parentElements.add(e);
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
				extractParentElements(node);
			}
			if(nodeContent.equals(XMLLookUpStrings.CHILDELEMENTS)){
				extractChildElements(node);
			}
		}
	}
	
	
	public void extractParentElements(Node node){
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
	
	public void extractChildElements(Node node){
		Node sibling = node.getNextSibling();
		while(sibling != null) {
		    if ( sibling.getNodeType() == Node.ELEMENT_NODE ) {
		    	Element ce = (Element)sibling;
				NodeList children = ce.getElementsByTagName(XMLLookUpStrings.COLOR);
				for (int i=0; i<children.getLength(); i++){
					Node child = children.item(i);
					String childName = trimString(child.getTextContent());
					if(childName != ""){
						this.addEleToChildElements(childName);
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
