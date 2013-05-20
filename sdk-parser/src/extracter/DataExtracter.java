package extracter;

import extractXML.DIVElement;


public interface DataExtracter {

	public void extractData(DIVElement elements);
	public void createData();
	public String getData();
	void createData(String version);
}
