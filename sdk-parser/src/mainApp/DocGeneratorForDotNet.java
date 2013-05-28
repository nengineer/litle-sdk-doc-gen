package mainApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import Locaters.FileLocater;

import combiner.LineMarkerForDotNet;

import extractXML.DIVElement;
import extractXML.ReadXMLFile;
//import Locaters.StringLocater;

public class DocGeneratorForDotNet {



	private final static String[] filenames = {"XmlRequestFields.cs", "XmlResponseFields.cs", "XmlFields.cs"};

	public static void main(String[] args){
		new DocGeneratorForDotNet().run("/usr/local/litle-home/zhe/parsePDF/xml_XML_Ref_elements.xml","/usr/local/litle-home/zhe/parsePDF/dotnet/LitleSdkForNet");
	}

	public void run(String fileaddress, String dirAddress){

		try{
			ReadXMLFile rd = new ReadXMLFile();
			rd.extractDIVs(fileaddress);


			// got element list extracted from xml file
			List<DIVElement> eList = rd.getDIVs();
			removeOldComments(dirAddress);

			for(DIVElement first : eList){
				if(!first.getEleName().trim().isEmpty()){
					// data extracted from DIV element

					first.generateElementDocForDotNet(dirAddress);

					// processing Enumeration of the Element
					first.generateEnumDocForDotNet(dirAddress);

				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void removeOldComments(String dirAddress){
	    for(String filename : DocGeneratorForDotNet.getFilenames()){
            FileLocater fl = new FileLocater();
            fl.locate(filename, dirAddress);
            try {
            if(fl.getResult() != null){
                for(String fname: fl.getResult()){
                    File temp = new File(fname);
                    ArrayList<String> dataList = new ArrayList<String>();

                            BufferedReader reader = new BufferedReader(new FileReader(temp));
                            String currentLine;
                            while((currentLine = reader.readLine()) != null){
                                if(!currentLine.trim().contains(new LineMarkerForDotNet().getPatternToMatch())){
                                    dataList.add(currentLine);
                                }
                            }
                            reader.close();


                    File result = new File(fname);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(result));
                    for(String line: dataList){
                        writer.write(line + '\n');
                    }
                    writer.close();

                }
            }
            }catch(Exception e){
            e.printStackTrace();
            }
	    }
	}

	public static String[] getFilenames() {
		return filenames;
	}
}
