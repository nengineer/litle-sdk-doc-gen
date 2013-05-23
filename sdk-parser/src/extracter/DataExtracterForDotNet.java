package extracter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import extractXML.Attribute;
import extractXML.DIVElement;
//import java.util.HashMap;

public class DataExtracterForDotNet implements DataExtracter{

	String data;
	List<String> dataList = new ArrayList<String>();
	
	
	
	@Override
	public void extractData(DIVElement div) {
		// TODO Auto-generated method stub
		DataExtracterForDotNet dx = this;
		dx.addToDList("<summary>");
		if(!div.getDescrip().trim().isEmpty()){
			String[] parts = div.getDescrip().split("\\.");
			for(String p : parts){
				dx.getDList().add(p.trim());
			}
		}
		dx.addToDList("</summary>");
		dx.addToDList("<remarks>");
		if(!div.getAttrs().isEmpty()){
			dx.addToDList("This is a list of its attributes. ");
			for(Entry<String, String> e: div.getAttrs().entrySet()){
				dx.getDList().add(e.getKey().trim()+ ": "+e.getValue().trim());
			}
		}
		if(!div.getParentElements().isEmpty()){
			dx.addToDList("This is a list of its parent elements. ");
			for(String p : div.getParentElements()){
				dx.getDList().add(p);
			}
		}

		
		if(!div.getChildElements().isEmpty()){
			dx.getDList().add("This is a list of its child elements. ");
			for(Entry<String,String> e : div.getChildElements().entrySet()){
				dx.getDList().add(e.getKey() + " : it is " + e.getValue());
			}
		}

		
		if(!div.getNotes().isEmpty()){
			dx.addToDList("<b>Notes: ");
			for(String note : div.getNotes()){
				dx.getDList().add(note);
			}
			dx.addToDList("</b>");
		}
		dx.addToDList("</remarks>");

		


		
		
		// add further description here		
	}
	
	public void extractDataForAttr(Attribute a){
		
		DataExtracterForDotNet da = this;
		
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
		
		DataExtracterForDotNet de = this;
		
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
		DataExtracterForDotNet dx = this;
		for(String s : dx.getDList()){
			dx.setData(dx.getData()+ "/// " + s + "\n");
		}		
	}

	@Override
	public void createData(String version) {
		DataExtracterForDotNet dx = this;
		for(String s : dx.getDList()){
			dx.setData(dx.getData()+ "/// " + s + "\n");
		}			
		dx.setData(dx.getData() + "/// <version>" + version + "</version>");
	}
	
	public void setData(String d){
		this.data = d;
	}

	@Override
	public String getData() {
		return this.data;
	}
	
	public List<String> getDList(){
		return this.dataList;
	}
	
	public void addToDList(String d){
		this.dataList.add(d);
	}

}
 