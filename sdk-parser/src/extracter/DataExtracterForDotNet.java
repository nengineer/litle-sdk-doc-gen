package extracter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import extractXML.Attribute;
import extractXML.DIVElement;
import formatter.StringFormatter;

public class DataExtracterForDotNet implements DataExtracter{

	private String data;
	private List<String> dataList;

	public DataExtracterForDotNet(){
		this.data = "";
		this.dataList = new ArrayList<String>();
	}

	@Override
	public void extractData(DIVElement div) {
		if(!div.getDescrip().trim().isEmpty()){
		    this.addToDList("<summary>");
	        for (String s: new StringFormatter().splitSentences(div.getDescrip().trim())) {
	          this.addToDList(s);
	        }

			this.addToDList("</summary>");
		}

		this.addToDList("<remarks>");
		if(!div.getAttrs().isEmpty()){
			this.addToDList("This is a list of its attributes. ");
			this.addToDList("<list type=\"bullet\">");
			for(Entry<String, String> e: div.getAttrs().entrySet()){
				this.addToDList("<item><description>"+e.getKey().trim()+ ": "+e.getValue().trim()+"</description></item>");
			}
			this.addToDList("</list>");
		}
		if(!div.getParentElements().isEmpty()){
			this.addToDList("This is a list of its parent elements. ");
			this.addToDList("<list type=\"bullet\">");
			for(String p : div.getParentElements()){
				this.addToDList("<item><description>"+p+"</description></item>");
			}
		}

		if(!div.getChildElements().isEmpty()){
			this.getDList().add("This is a list of its child elements. ");
			this.addToDList("<list type=\"bullet\">");
			for(Entry<String,String> e : div.getChildElements().entrySet()){
				this.addToDList("<item><description>"+e.getKey() + " : it is " + e.getValue()+"</description></item>");
			}
		}

		if(!div.getNotes().isEmpty()){
			for(String note : div.getNotes()){
			    this.addToDList("<b>Note: ");
				for(String sentence: new StringFormatter().splitSentences(note)){
				    this.addToDList(sentence);
				}
				this.addToDList("</b>");
			}

		}
		this.addToDList("</remarks>");
		// add further description here
	}

	public void extractDataForEnum(String data){

		DataExtracterForDotNet de = this;
		de.addToDList("<summary>");

        for (String s: new StringFormatter().splitSentences(data)) {
          de.addToDList(s);
        }
		de.addToDList("</summary>");

	}

	public void extractDataForAttr(Attribute a){
	    this.addToDList("<summary>");
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
        this.addToDList("</summary>");
        this.addToDList("<remarks>");
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
            this.addToDList("<b>Note: ");
            for(String note : new StringFormatter().splitSentences(a.getNote())){
                    this.addToDList(note);
            }
            this.addToDList("</b>");
        }
        if(!a.getExtra().isEmpty()){
            for (String s: new StringFormatter().splitSentences(a.getExtra().trim())) {
                this.addToDList(s);
              }
        }
        this.addToDList("</remarks>");
	}

	@Override
	public void createData() {
		for(String s : this.getDList()){
			this.setData(this.getData()+ "/// " + s + "\n");
		}
		this.setData(this.getData() + "/// <author>sdksupport@litle.com</author>");
	}

	@Override
	public void createData(String version) {
		for(String s : this.getDList()){
			this.setData(this.getData()+ "/// " + s + "\n");
		}
		this.setData(this.getData() + "/// <version>" + version + "</version>\n");
		this.setData(this.getData() + "/// <author>sdksupport@litle.com</author>");
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
