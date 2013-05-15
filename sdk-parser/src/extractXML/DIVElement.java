package extractXML;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DIVElement {

	private String eleName;
	private String eleType;
	private String descrip;
	private HashMap<String, String> attrs;
	private ArrayList<String> parentElements;
	private HashMap<String, String> childElements;

	// private ArrayList<Attribute> attributes;

	public DIVElement() {
		this.eleName = "";
		this.descrip = "";
		this.attrs = new HashMap<String, String>();
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

	public void addEleToParentElements(String e) {
		this.parentElements.add(e);
	}

	public HashMap<String, String> getChildElements() {
		return childElements;
	}

	public void setChildElements(HashMap<String, String> childElements) {
		this.childElements = childElements;
	}

	public void addEleToChildElements(String e, String option) {
		this.childElements.put(e, option);
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	
	public void appendDescrip(String newDescrip){
		this.descrip = this.descrip + newDescrip;
	}


	
	public void addEleToAttrs(String key, String value) {
		this.attrs.put(key, value);
	}

	public HashMap<String, String> getAttrs() {
		return attrs;
	}

	public void setAttrs(HashMap<String, String> attrs) {
		this.attrs = attrs;
	}

	/**
	 * process the whole block of XML element
	 * 
	 * @param div
	 */
	public void processDIV(Element div) {
		this.extractItemName(div);
		System.out.println("Item Name: " + this.eleName);
		System.out.println("Description: " + this.descrip);
		for (Entry<String, String> i : attrs.entrySet()) {
			System.out.println("Key Attributes: " + i.getKey() + ", "
					+ i.getValue());
		}
		this.extractSubHeaderElement(div);
		for (String s : parentElements) {
			System.out.println("Parent Element: " + s);
		}
		for (Entry<String, String> i : childElements.entrySet()) {
			System.out.println("Child Element: " + i.getKey() + ", "
					+ i.getValue());
		}
	}

	/**
	 * extract the name of the XML element
	 * 
	 * @param div
	 */
	public void extractItemName(Element div) {
		NodeList nodes = div.getElementsByTagName(XMLLookUpStrings.H1_HEAD1);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node eleA = nodes.item(i);
			if (eleA.getNodeType() == Node.ELEMENT_NODE) {
				String itemName = trimWhiteSpace(eleA.getTextContent());
				if (itemName != "") {
					this.setEleName(itemName);
				}
				extractDescription(eleA);
			}
		}
	}

	/**
	 * extract the brief description of the element. It can be one or two short paragraphs
	 * @param node
	 */
	public void extractDescription(Node node) {
		Node sibling = node.getNextSibling();
		while (sibling != null ) {
			if (sibling.getNodeType() == Node.ELEMENT_NODE) {
				Element se = (Element) sibling;
				if(se.getTagName().equals(XMLLookUpStrings.BP_BODY1) && !stopForDescrip(se.getTextContent())){
					String s1 = trimNewLine(se.getTextContent());
					appendDescrip(s1);
				}
				else{
					break;
				}
			}
			sibling = sibling.getNextSibling();
		}
		extractTypeOptional(sibling);

	}

	public void extractTypeOptional(Node sibling) {
		while (sibling != null) {
			if (sibling.getNodeType() == Node.ELEMENT_NODE) {
				Element se = (Element) sibling;
				if(se.getTagName().equals(XMLLookUpStrings.BP_BODY1)){
					String s1 = trimNewLine(se.getTextContent());
					formatTypes(s1);
					
				}
				break;
			}
			sibling = sibling.getNextSibling();
		}
	}

	/**
	 * extract sub header elements including parentElements, childElements, and
	 * attributes
	 * 
	 * @param div
	 */
	public void extractSubHeaderElement(Element div) {
		NodeList nodes = div
				.getElementsByTagName(XMLLookUpStrings.SH1_SUBHEAD1);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			String nodeContent = trimWhiteSpace(node.getTextContent());
			if (nodeContent.equals(XMLLookUpStrings.PARENTELEMENTS)) {
				extractParentElements(node);
			}
			if (nodeContent.equals(XMLLookUpStrings.CHILDELEMENTS)) {
				extractChildElements(node);
			}
		}
	}

	/**
	 * extract parent elements 
	 * @param node
	 */
	public void extractParentElements(Node node) {
		Node sibling = node.getNextSibling();
		while (sibling != null) {
			if (sibling.getNodeType() == Node.ELEMENT_NODE) {
				Element pe = (Element) sibling;
				NodeList parents = pe
						.getElementsByTagName(XMLLookUpStrings.COLOR);
				for (int i = 0; i < parents.getLength(); i++) {
					Node parent = parents.item(i);
					String parentName = trimWhiteSpace(parent.getTextContent());
					if (parentName != "") {
						this.addEleToParentElements(parentName);
					}
				}
				break;
			}
			
			sibling = sibling.getNextSibling();
		}
	}

	/**
	 * extract child elements
	 * @param node
	 */
	public void extractChildElements(Node node) {
		Node sibling = node.getNextSibling();
		int count = 0; // there are at most two sets of childElements: required
						// and optional
		while (sibling != null) {
			if (sibling.getNodeType() == Node.ELEMENT_NODE) {
				count++;
				Element ce = (Element) sibling;
				String option = formatChildElementsOption(ce.getTextContent());
				NodeList children = ce
						.getElementsByTagName(XMLLookUpStrings.COLOR);
				for (int i = 0; i < children.getLength(); i++) {
					Node child = children.item(i);
					String childName = trimWhiteSpace(child.getTextContent());
					if (childName != "") {
						this.addEleToChildElements(childName, option);
					}
				}
				if (count >= 2) {
					break;
				}

			}
			sibling = sibling.getNextSibling();
		}
	}

	
	private String formatChildElementsOption(String s) {
	
		return trimWhiteSpace(s).replaceAll(":.*", "");
	}
	
	private void formatTypes(String s){
		String [] entries = s.split(";");
		
		for (String entry: entries){
			String key = entry.replaceAll(" = .*", "");
			String value = entry.replaceAll(".* = ", "");
			this.addEleToAttrs(key, value);
		}
	
	}

	private String trimWhiteSpace(String raw) {
		return raw.replaceAll("\\s", "");
	}

	private String trimNewLine(String raw) {
		return raw.replaceAll("\\n", "");
	}
	
	private boolean stopForDescrip(String match){
		Pattern p = Pattern.compile(".* = .*");
		Matcher m = p.matcher(match);
		return m.find();
	}

}
