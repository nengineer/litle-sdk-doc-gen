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
		if(!div.getDescrip().trim().isEmpty()){
			if(div.getDescrip().split("\\.").length > 1){
				String[] parts = div.getDescrip().split("\\.");
				
				for(String p : parts){
				    this.getDList().add(p.trim());
				}
				
			}else{
			    this.getDList().add(div.getDescrip());
			}
		}
		this.getDList().add("The " + div.getEleName() + "field is required by :");
		List<String> plist = new ArrayList<String>();		
		plist = div.getParentElements();
		for(String p : plist){
		    this.getDList().add("@see " + p);
		}
		
		if(!div.getNotes().isEmpty()){
		    this.getDList().add("<b>Note : ");
			
			for(String note : div.getNotes()){
			    this.getDList().add(note);
			}
			
			this.getDList().add("</b>");
		}
		
		
		
		for(Entry<String, String> e: div.getAttrs().entrySet()){
		    this.getDList().add(e.getKey().trim() + " : " + e.getValue().trim());
		}
		
		if(!div.getChildElements().isEmpty()){
		    this.getDList().add("Child Elements : ");
			for(Entry<String,String> e : div.getChildElements().entrySet()){
			    this.getDList().add(e.getKey() + " : it is " + e.getValue());
			}
		}

		
		
		// add further description here		
	}
	
	public void extractDataForAttr(Attribute a){
		
		
	    this.getDList().add("Name : " + a.getName());
	    this.getDList().add("Type : " + a.getType());
		if(a.isRequired()){
		    this.getDList().add("It is required");
		}else{
		    this.getDList().add("It is optional");
		}
		
		if(a.getDescription().split("\\.").length > 1){
			String[] parts = a.getDescription().split("\\.");
			
			for(String p : parts){
			    this.getDList().add(p.trim());
			}
		}else{
		    this.getDList().add(a.getDescription());
		}
		if(!a.getMaxLength().isEmpty()){
		    this.getDList().add("Max length : " + a.getMaxLength());
		}
		if(!a.getMinLength().isEmpty()){
		    this.getDList().add("Min length : " + a.getMinLength());
		}
		if(!a.getTotalDigits().isEmpty()){
		    this.getDList().add("Total digits : " + a.getTotalDigits());
		}
		if(!a.getValidValues().isEmpty()){
		    this.getDList().add("Valid Values : " + a.getValidValues());
		}
		if(!a.getNote().isEmpty()){
			if(a.getNote().split("\\.").length > 1){
			    this.getDList().add("<b>Notes : " + "\n");
				String[] parts = a.getNote().split("\\.");
				for(String p : parts){
				    this.getDList().add(p.trim());
				}	
			}else{
			    this.getDList().add("<b>Note : " + a.getNote());
			}
			this.getDList().add("</b>");
		}
		
		if(!a.getExtra().isEmpty()){
			if(a.getExtra().split("\\.").length > 1){
				//da.getDList().add("Notes : " + "\n");
				String[] parts = a.getExtra().split("\\.");
				for(String p : parts){
				    this.getDList().add(p.trim());
				}	
			}else{
			    this.getDList().add(a.getExtra());
			}	
		}
		// add further description here		
	}
	
	public void extractDataForEnum(String data){
		
		if(data.split("\\.").length > 1){
			String[] parts = data.split("\\.");
			
			for(String p : parts){
			    this.getDList().add(p.trim());
			}
		}else{
		    this.getDList().add(data);
		}
			
	}
	
	
	
	@Override
	public void createData() {
		// TODO Auto-generated method stub
		
		this.setData("/**" + "\n");
		for(String s : this.getDList()){
		    this.setData(this.getData() + " * " + s + "\n");
		}		
		
		//dx.setData(dx.getData() + " *@version : " + version);
		
		this.setData(this.getData() + " */");
	}
	
	

	@Override
	public void createData(String version) {
		// TODO Auto-generated method stub
		
		this.setData("/**" + "\n");
		for(String s : this.getDList()){
		    this.setData(this.getData() + " * " + s + "\n");
		}		
		
		this.setData(this.getData() + " *@author : sdksupport@litle.com");
		
		this.setData(this.getData() + " *@version : " + version);
		
		this.setData(this.getData() + " */");
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
 