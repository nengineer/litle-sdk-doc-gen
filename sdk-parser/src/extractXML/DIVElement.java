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
		eleName = "No Name";
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

	public ArrayList<String> getChildElements() {
		return childElements;
	}

	public void setChildElements(ArrayList<String> childElements) {
		this.childElements = childElements;
	}

	
	
	public void processDIV(Element div){
		this.extractItemName(div);
		System.out.println("Item Name: " + eleName);
		//extractParentElement(div);
	}
	
	public void extractItemName(Element div){
		NodeList nodes = div.getElementsByTagName("H1-Head1");  
		for(int i=0; i< nodes.getLength(); i++){
				Node eleA = nodes.item(i);
				if(eleA.getNodeType() == Node.ELEMENT_NODE){
					String itemName = eleA.getTextContent().replaceAll("\\s","");
					if(itemName != ""){
						this.setEleName(itemName);
					}
				}
		}
	}
		
	public void extractParentElement(Element div){
		
	}

	
	

}
