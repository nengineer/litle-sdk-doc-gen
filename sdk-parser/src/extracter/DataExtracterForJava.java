package extracter;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;


import extractXML.DIVElement;

public class DataExtracterForJava implements DataExtracter{

	String data;
	List<String> dataList = new ArrayList<String>();
	
	
	
	@Override
	public void extractData(DIVElement div) {
		// TODO Auto-generated method stub
		DataExtracterForJava dx = this;
		
		dx.getDList().add("The " + div.getEleName() + "field is required by :");
		
		List<String> plist = new ArrayList<String>();
		
		plist = div.getParentElements();
		
		for(String p : plist){
			dx.getDList().add("@see " + p);
		}
		
		dx.getDList().add("Demo to Zhen ");
		dx.getDList().add("@version : not added");
		dx.getDList().add("Demo for Greg");
		
		// add further description here		
	}

	@Override
	public void createData() {
		// TODO Auto-generated method stub
		
		DataExtracterForJava dx = this;
		dx.setData("/*" + "\n");
		for(String s : dx.getDList()){
			dx.setData(dx.getData() + " * " + s + "\n");
		}		
		dx.setData(dx.getData() + " */");
	}
	
	public void setData(String d){
		this.data = d;
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return this.data;
	}
	
	public List<String> getDList(){
		return this.dataList;
	}

}
