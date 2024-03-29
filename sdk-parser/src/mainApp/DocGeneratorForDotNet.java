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

import extractXML.Attribute;
import extractXML.DIVElement;
import extractXML.ReadXMLFile;
//import Locaters.StringLocater;

public class DocGeneratorForDotNet {

	private final static String[] filenames = {"XmlRequestFields.cs", "XmlResponseFields.cs", "XmlFields.cs"};

	public void run(String fileaddress, String dirAddress, String version){

		try{
			ReadXMLFile rd = new ReadXMLFile();
			rd.extractDIVs(fileaddress);


			// got element list extracted from xml file
			List<DIVElement> eList = rd.getDIVs();
			removeOldComments(dirAddress);

			for(DIVElement first : eList){
				if(!first.getEleName().trim().isEmpty()){
					// data extracted from DIV element

					first.generateElementDocForDotNet(dirAddress, version);

					// processing Enumeration of the Element
					if(!first.getEnumerations().isEmpty()){
					    first.generateEnumDocForDotNet(dirAddress, version);
					}
					if(!first.getSubElements().isEmpty()){
                        for(Attribute a : first.getSubElements()){
                            a.generateAttributesDocForDotNet(first, dirAddress, version);

                        }
					}



				}
			}
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println("Fields without documentation::");
			for(DIVElement ele : eList){
			    if(!ele.getEleName().trim().isEmpty()){
			        if(ele.getNChanges() == 0){
			            System.out.println("Element : " + ele.getEleName());
			        }
			        for(Attribute attr : ele.getSubElements()){
			            if(attr.getNChanges() == 0){
			                System.out.println("Attribute : " + attr.getName() + 
			                        " for Element : " + ele.getEleName());
			            }
			        }
			    }
			}
	            
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
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
                                if(!currentLine.trim().startsWith(LineMarkerForDotNet.getCommentprefix())){
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
