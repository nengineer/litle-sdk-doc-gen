package mainApp;

import java.util.List;

import extractXML.DIVElement;
import extractXML.ReadXMLFile;
//import Locaters.StringLocater;

public class DocGeneratorForDotNet {



	private final static String[] filenames = {"XmlRequestFields.cs", "XmlResponseFields.cs"};

	public static void main(String[] args){
		new DocGeneratorForDotNet().run("/usr/local/litle-home/zhe/parsePDF/xml_XML_Ref_elements.xml","/usr/local/litle-home/zhe/parsePDF/dotnet/LitleSdkForNet");
	}

	public void run(String fileaddress, String dirAddress){

		try{
			ReadXMLFile rd = new ReadXMLFile();
			rd.extractDIVs(fileaddress);


			// got element list extracted from xml file
			List<DIVElement> eList = rd.getDIVs();

			for(DIVElement first : eList){
				if(!first.getEleName().trim().isEmpty()){
					// data extracted from DIV element
					first.generateElementDocForDotNet(dirAddress);

					// processing Enumeration of the Element
					//first.generateEnumDocForDotNet(dirAddress);

				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static String[] getFilenames() {
		return filenames;
	}
}
