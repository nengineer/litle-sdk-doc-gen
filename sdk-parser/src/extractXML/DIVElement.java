package extractXML;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DIVElement {
	
	private String eleName;
	private String eleType;
	private ArrayList<String> parentElements;
	private HashMap<String, String> childElements; 
	//private ArrayList<Attribute> attributes;
	
	public DIVElement(){
		this.eleName = "";
		this.parentElements = new ArrayList<String>();
		this.childElements = new HashMap<String, String>();
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

	public HashMap<String, String> getChildElements() {
		return childElements;
	}

	public void setChildElements(HashMap<String, String> childElements) {
		this.childElements = childElements;
	}
	
	public void addEleToChildElements(String e, String option){
		this.childElements.put(e, option);
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
		for(Entry<String, String> i: childElements.entrySet()){
			System.out.println("Child Element: "+i.getKey()+", "+i.getValue());
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
					extractDescription(eleA);
				}
		}
	}
	
	public void extractDescription(Node node){
		Node sibling = node.getNextSibling();
		while(sibling != null) {
		    if ( sibling.getNodeType() == Node.ELEMENT_NODE ) {
		    	//System.out.println(sibling.getTextContent().replaceAll("\n", ""));
		    	String s1 = sibling.getTextContent().replaceAll("\n", "");
		    	System.out.println(s1.replaceAll("\b[a-zA-Z0-9_],|, and |, ", "\n@"));
		    	formatDescription(sibling.getTextContent());
		    	break;
		    }
		    sibling = sibling.getNextSibling();
		}
		
	}
	
	private String formatDescription(String raw){
		return "";
	}
	
	/**
	 * extract sub header elements including parentElements, childElements, and attributes
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
		int  count = 0; //there are at most two sets of childElements: required and optional
		while(sibling != null) {
		    if ( sibling.getNodeType() == Node.ELEMENT_NODE ) {
		    	count++;
		    	Element ce = (Element)sibling;
		    	String option = trimString(ce.getTextContent()).replaceAll(":.*", "");
				NodeList children = ce.getElementsByTagName(XMLLookUpStrings.COLOR);
				for (int i=0; i<children.getLength(); i++){
					Node child = children.item(i);
					String childName = trimString(child.getTextContent());
					if(childName != ""){
						this.addEleToChildElements(childName, option);
					}
				}
				if(count>=2){
					break;
				}
				
		    }
		    sibling = sibling.getNextSibling();
		}
	}
	
	private String trimString(String raw){
		return raw.replaceAll("\\s","");
	}

	
	

}
