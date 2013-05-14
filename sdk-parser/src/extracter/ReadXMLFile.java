package extracter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

public class ReadXMLFile {

	List<Employee> empList = new ArrayList<Employee>();
	 
	  public static void main(String argv[]) {
	 
	    try {
	 
	    	File fXmlFile = new File("/usr/local/litle-home/vchouhan/Desktop/project/staff.xml");
		
	    	new ReadXMLFile().extractEmp(fXmlFile);
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	  }
	  
	  public void extractEmp(File fxml){
		  ReadXMLFile rd = this;
		  try{
			  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			  Document doc = dBuilder.parse(fxml);
			 
			  doc.getDocumentElement().normalize();
				 
			  NodeList nList = doc.getElementsByTagName("employee");
				
			  for (int temp = 0; temp < nList.getLength(); temp++) {
					 
				  Node nNode = nList.item(temp);
				  if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
					  	Element eElement = (Element) nNode;
					  	
					  	Employee eTemp = new Employee();
					  	
					  	eTemp.setId(Integer.parseInt(eElement.getAttribute("id")));
					  	eTemp.setFirstName(eElement.getElementsByTagName("firstname").item(0).getTextContent());
					  	eTemp.setLastName(eElement.getElementsByTagName("lastname").item(0).getTextContent());
					  	eTemp.setNickName(eElement.getElementsByTagName("nickname").item(0).getTextContent());
					  	eTemp.setSalary(Integer.parseInt(eElement.getElementsByTagName("salary").item(0).getTextContent()));  
					  	
					  	rd.getEmpList().add(eTemp);
					    System.out.println("successfully added " + rd.getEmpList().size() + " element to the list");
						}
				
			  	}
		  }catch (Exception e) {
			  e.printStackTrace();
		  }
	  }

	public  List<Employee> getEmpList() {
		// TODO Auto-generated method stub
		return this.empList;
	}
	  
}
