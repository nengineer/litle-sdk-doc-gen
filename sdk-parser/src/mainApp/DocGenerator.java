package mainApp;

import java.io.File;
import java.util.List;

import combiner.ContentCombiner;

import Locaters.FileLocater;
//import Locaters.StringLocater;
import Locaters.StringLocaterForJava;

import extracter.DataExtracterForJava;
import extractXML.DIVElement;
import extractXML.ReadXMLFile;

import extracter.Employee;

public class DocGenerator {

	public static void main(String[] args){
		new DocGenerator().run("/usr/local/litle-home/vchouhan/Desktop/testarena/staff.xml","/usr/local/litle-home/vchouhan/Desktop/testarena");
	}
	
	
	
	public void run(String fileaddress, String dirAddress){
		
		try{
			File fxml = new File(fileaddress);
			
			ReadXMLFile rd = new ReadXMLFile();
			//rd.extractEmp(fxml);
			//rd.extractDIVs(fxml);
			
			List<DIVElement> eList = rd.getDIVs();
			
			DataExtracterForJava dx = new DataExtracterForJava();
			dx.extractData(eList.get(0));
			
			dx.createData();
			
			String payLoad = dx.getData();
			
			
			
			
			
			
			
			
			
			//List<Employee> empList = rd.getEmpList();
			
			
			
			/**
			for(Employee e : empList){
				DataExtracterForJava dx = new DataExtracterForJava();
				dx.extractData(e);
				dx.createData();
				String payLoad = dx.getData();
				
				System.out.println("data extracter working....");
				
				
				FileLocater fl = new FileLocater();
				fl.locate(e.getNickName() + ".java", dirAddress);
				
				System.out.println("file locater working....");
				
				
				List<String> files = fl.getResult();
				
				
				for(String f : files){
					System.out.println(f);
				}
				
				for(String f : files){
					StringLocaterForJava sl = new StringLocaterForJava(f);
					sl.findLocations("setname;");
					List<Integer> locations = sl.getLocations();
					
					for(int l : locations){
						new ContentCombiner(l).combine(new File(f), payLoad);
					}

				}
				
				System.out.println("String locater and content combiner working....");
				
				
			}*/
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
}
