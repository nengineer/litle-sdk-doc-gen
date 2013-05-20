package extracter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import extractXML.Attribute;
import extractXML.DIVElement;
//import java.util.HashMap;

public class DataExtracterForJava implements DataExtracter{

	String data;
	List<String> dataList = new ArrayList<String>();
	
	
	
	@Override
	public void extractData(DIVElement div) {
		// TODO Auto-generated method stub
		DataExtracterForJava dx = this;
		if(!div.getDescrip().trim().isEmpty()){
			if(div.getDescrip().split("\\.").length > 1){
				String[] parts = div.getDescrip().split("\\.");
				
				for(String p : parts){
					dx.getDList().add(p.trim());
				}
				
			}else{
				dx.getDList().add(div.getDescrip());
			}
		}
		dx.getDList().add("The " + div.getEleName() + "field is required by :");
		List<String> plist = new ArrayList<String>();		
		plist = div.getParentElements();
		for(String p : plist){
			dx.getDList().add("@see " + p);
		}
		
		if(!div.getNotes().isEmpty()){
			dx.getDList().add("<b>Note : ");
			
			for(String note : div.getNotes()){
				dx.getDList().add(note);
			}
			
			dx.getDList().add("</b>");
		}
		
		
		
		for(Entry<String, String> e: div.getAttrs().entrySet()){
			dx.getDList().add(e.getKey().trim() + " : " + e.getValue().trim());
		}
		
		if(!div.getChildElements().isEmpty()){
			dx.getDList().add("Child Elements : ");
			for(Entry<String,String> e : div.getChildElements().entrySet()){
				dx.getDList().add(e.getKey() + " : it is " + e.getValue());
			}
		}

		
		
		// add further description here		
	}
	
	public void extractDataForAttr(Attribute a){
		
		DataExtracterForJava da = this;
		
		da.getDList().add("Name : " + a.getName());
		da.getDList().add("Type : " + a.getType());
		if(a.isRequired()){
			da.getDList().add("It is required");
		}else{
			da.getDList().add("It is optional");
		}
		
		if(a.getDescription().split("\\.").length > 1){
			String[] parts = a.getDescription().split("\\.");
			
			for(String p : parts){
				da.getDList().add(p.trim());
			}
		}else{
			da.getDList().add(a.getDescription());
		}
		if(!a.getMaxLength().isEmpty()){
			da.getDList().add("Max length : " + a.getMaxLength());
		}
		if(!a.getMinLength().isEmpty()){
			da.getDList().add("Min length : " + a.getMinLength());
		}
		if(!a.getTotalDigits().isEmpty()){
			da.getDList().add("Total digits : " + a.getTotalDigits());
		}
		if(!a.getValidValues().isEmpty()){
			da.getDList().add("Valid Values : " + a.getValidValues());
		}
		if(!a.getNote().isEmpty()){
			if(a.getNote().split("\\.").length > 1){
				da.getDList().add("<b>Notes : " + "\n");
				String[] parts = a.getNote().split("\\.");
				for(String p : parts){
					da.getDList().add(p.trim());
				}	
			}else{
				da.getDList().add("<b>Note : " + a.getNote());
			}
			da.getDList().add("</b>");
		}
		
		if(!a.getExtra().isEmpty()){
			if(a.getExtra().split("\\.").length > 1){
				//da.getDList().add("Notes : " + "\n");
				String[] parts = a.getExtra().split("\\.");
				for(String p : parts){
					da.getDList().add(p.trim());
				}	
			}else{
				da.getDList().add(a.getExtra());
			}	
		}
		// add further description here		
	}
	
	public void extractDataForEnum(String data){
		
		DataExtracterForJava de = this;
		
		if(data.split("\\.").length > 1){
			String[] parts = data.split("\\.");
			
			for(String p : parts){
				de.getDList().add(p.trim());
			}
		}else{
			de.getDList().add(data);
		}
			
	}
	
	
	
	@Override
	public void createData() {
		// TODO Auto-generated method stub
		
		DataExtracterForJava dx = this;
		dx.setData("/**" + "\n");
		for(String s : dx.getDList()){
			dx.setData(dx.getData() + " * " + s + "\n");
		}		
		
		//dx.setData(dx.getData() + " *@version : " + version);
		
		dx.setData(dx.getData() + " */");
	}
	
	

	@Override
	public void createData(String version) {
		// TODO Auto-generated method stub
		
		DataExtracterForJava dx = this;
		dx.setData("/**" + "\n");
		for(String s : dx.getDList()){
			dx.setData(dx.getData() + " * " + s + "\n");
		}		
		
		dx.setData(dx.getData() + " *@version : " + version);
		
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
 