package extracter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import extractXML.Attribute;
import extractXML.DIVElement;
import formatter.StringFormatter;

public class DataExtracterForJava implements DataExtracter{

	private String data;
	private List<String> dataList = new ArrayList<String>();



	@Override
	public void extractData(DIVElement div) {
		if(!div.getDescrip().trim().isEmpty()){
            for (String s: new StringFormatter().splitSentences(div.getDescrip().trim())) {
                this.addToDList(s);
              }
		}
		this.addToDList("The " + div.getEleName() + "field is required by :");
		List<String> plist = new ArrayList<String>();
		plist = div.getParentElements();
		for(String p : plist){
		    this.addToDList("@see " + p);
		}

		if(!div.getNotes().isEmpty()){
			for(String note : div.getNotes()){
			    this.addToDList("<b>Note : ");
	            for(String sentence: new StringFormatter().splitSentences(note)){
	                    this.addToDList(sentence);
	            }
			    this.addToDList("</b>");
			}
		}

		for(Entry<String, String> e: div.getAttrs().entrySet()){
		    this.addToDList(e.getKey().trim() + " : " + e.getValue().trim());
		}

		if(!div.getChildElements().isEmpty()){
		    this.addToDList("Child Elements : ");
			for(Entry<String,String> e : div.getChildElements().entrySet()){
			    this.addToDList(e.getKey() + " : it is " + e.getValue());
			}
		}
		// add further description here
	}

	public void extractDataForAttr(Attribute a){
	    this.addToDList("Name : " + a.getName());
	    this.addToDList("Type : " + a.getType());
		if(a.isRequired()){
		    this.addToDList("It is required");
		}else{
		    this.addToDList("It is optional");
		}

        if(!a.getDescription().trim().isEmpty()){
            for (String s: new StringFormatter().splitSentences(a.getDescription().trim())) {
                this.addToDList(s);
              }
        }
		if(!a.getMaxLength().isEmpty()){
		    this.addToDList("Max length : " + a.getMaxLength());
		}
		if(!a.getMinLength().isEmpty()){
		    this.addToDList("Min length : " + a.getMinLength());
		}
		if(!a.getTotalDigits().isEmpty()){
		    this.addToDList("Total digits : " + a.getTotalDigits());
		}
		if(!a.getValidValues().isEmpty()){
		    this.addToDList("Valid Values : " + a.getValidValues());
		}
		if(!a.getNote().isEmpty()){
			if(a.getNote().split("\\.").length > 1){
			    this.addToDList("<b>Notes : " + "\n");
				String[] parts = a.getNote().split("\\.");
				for(String p : parts){
				    this.addToDList(p.trim());
				}
			}else{
			    this.addToDList("<b>Note : " + a.getNote());
			}
			this.addToDList("</b>");
		}

		if(!a.getExtra().isEmpty()){
            for (String s: new StringFormatter().splitSentences(a.getExtra().trim())) {
                this.addToDList(s);
              }
		}
		// add further description here
	}

	public void extractDataForEnum(String data){
        if(!data.isEmpty()){
            for (String s: new StringFormatter().splitSentences(data.trim())) {
                this.addToDList(s);
              }
        }

	}

	@Override
	public void createData() {
		this.setData("/**" + "\n");
		for(String s : this.getDList()){
		    this.setData(this.getData() + " * " + s + "\n");
		}
		this.setData(this.getData() + " */");
	}



	@Override
	public void createData(String version) {
		this.setData("/**" + "\n");
		for(String s : this.getDList()){
		    this.setData(this.getData() + " * " + s + "\n");
		}

		this.setData(this.getData() + " *@author : sdksupport@litle.com\n");

		this.setData(this.getData() + " *@version : " + version + "\n");

		this.setData(this.getData() + " */");
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

	public void addToDList(String e){
	    this.dataList.add(e);
	}

}
