package mainApp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Locaters.FileLocater;

import combiner.ContentCombiner;
import combiner.LineMarkerForJava;
import combiner.LineMarkerForRuby;

import extractXML.Attribute;
import extractXML.DIVElement;
import extractXML.ReadXMLFile;
import extracter.DataExtracterForRuby;

public class DocGeneretorForRuby {
    
    public void run(String fileaddress, String dirAddress, String version){
        
        try{
            ReadXMLFile rd = new ReadXMLFile();
            rd.extractDIVs(fileaddress);
            
            
            // got element list extracted from XML file
            List<DIVElement> eList = rd.getDIVs();

            // cleaning the old auto generated comments from the java package 
            Map<String, Integer> noChangeMap = new HashMap<String, Integer>();
            
            appendToMap(eList, noChangeMap);
            
            FileLocater fnew = new FileLocater();
            
            fnew.locate("xmlfields.rb", dirAddress);
            
            Map<Integer, List<String>> appendmap = new HashMap<Integer, List<String>>();
            
            
            
            for(String add : fnew.getResult()){
                if(new File(add).canWrite()){
                    removeOldCommentsFromFile(add,eList,noChangeMap);
                    System.out.println("processing file : " + add);
                    ContentCombiner cnew = new ContentCombiner(new ArrayList<Integer>(), new LineMarkerForRuby());
                    cnew.storeContent(new File(add));

                    for(DIVElement e : eList){
                        if(!e.getEleName().trim().isEmpty()){
                            generateElementCommentsForRuby(e,noChangeMap,version,cnew, appendmap);

                            for(Attribute a : e.getSubElements()){
                                generateAttributeCommentsForRuby(a,e,noChangeMap,version,cnew,appendmap);
                            }
                        }
                    }
                    cnew.appendContent(new File(add), appendmap);
                    System.out.println("processing completed....");
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * creates a list of all the names of the elements and attributes to avoid duplication
     * @param eList
     * @param noChangeMap
     */
    public void appendToMap(List<DIVElement> eList, Map<String, Integer> noChangeMap){
        for(DIVElement e : eList){
            if(!e.getEleName().trim().isEmpty()){
                if(!noChangeMap.containsKey(e.getEleName().toLowerCase()))
                    noChangeMap.put(e.getEleName().toLowerCase(), 0);
                
                for(Attribute a : e.getSubElements()){
                     if(!noChangeMap.containsKey(a.getName().toLowerCase()))
                            noChangeMap.put(a.getName().toLowerCase(), 0);
                }
            }
        }
    }
    
    /**
     * removes all the previous appended comments from the given file
     * @param add
     * @param eList
     * @param noChangeMap
     */
    public void removeOldCommentsFromFile(String add,List<DIVElement> eList, Map<String, Integer> noChangeMap){
          
        System.out.println("removing old comments in file : " + add);
            
        ContentCombiner cr = new ContentCombiner(new ArrayList<Integer>(), new LineMarkerForRuby());
        cr.storeContent(new File(add));
            
            
        for(DIVElement e : eList){
            if(!e.getEleName().trim().isEmpty()){
                if(noChangeMap.containsKey(e.getEleName().toLowerCase())){
                    //noChangeMap.remove(e.getEleName().toLowerCase());
                    for(int i = 0;i< cr.getDataList().size();i++){
                        if(cr.getDataList().get(i).toLowerCase().contains(":" +e.getEleName().toLowerCase() + ",")
                                && cr.getDataList().get(i).toLowerCase().contains("_node")
                                && cr.getDataList().get(i).toLowerCase().contains("default_value")){
                            if(!cr.getLocations().contains(i + 1))
                                cr.getLocations().add(i + 1);
                        }
                    }
                        
                    for(Attribute a : e.getSubElements()){
                        if(!a.getName().trim().isEmpty()){
                            if(noChangeMap.containsKey(a.getName().toLowerCase())){
                                //noChangeMap.remove(a.getName().toLowerCase());
                                for(int i = 0;i< cr.getDataList().size();i++){
                                    if(cr.getDataList().get(i).toLowerCase().contains(":" +a.getName().toLowerCase() + ",")
                                            && cr.getDataList().get(i).toLowerCase().contains("_node")
                                            && cr.getDataList().get(i).toLowerCase().contains("default_value")){
                                        if(!cr.getLocations().contains(i + 1))
                                            cr.getLocations().add(i + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        cr.processContent();
        cr.removeFlagged(new File(add));
        System.out.println("old comments removed!!!!");
    }
    
    public void generateElementCommentsForRuby(DIVElement e, Map<String, Integer> noChangeMap, String version, ContentCombiner cnew, Map<Integer, List<String>> appendmap){
        if(noChangeMap.containsKey(e.getEleName().toLowerCase())){
            noChangeMap.remove(e.getEleName().toLowerCase());
            DataExtracterForRuby dx = new DataExtracterForRuby();
            dx.extractData(e);
            dx.createData(version);
            for(int i = 0;i< cnew.getDataList().size();i++){
                if(cnew.getDataList().get(i).toLowerCase().contains(":" +e.getEleName().toLowerCase() + ",")
                        && cnew.getDataList().get(i).toLowerCase().contains("_node")
                        && cnew.getDataList().get(i).toLowerCase().contains("default_value")
                        && !cnew.getDataList().get(i).toLowerCase().contains("if")){
                    List<String> temp = new ArrayList<String>();
                    temp.add(dx.getData());
                    appendmap.put(i, temp);
                }
            }
        }
    }
    
    
    public void generateAttributeCommentsForRuby(Attribute a,DIVElement e, Map<String, Integer> noChangeMap, String version, ContentCombiner cnew, Map<Integer, List<String>> appendmap){
        if(!a.getName().trim().isEmpty()){
            if(noChangeMap.containsKey(a.getName().toLowerCase())){
                noChangeMap.remove(a.getName());
                DataExtracterForRuby da = new DataExtracterForRuby();
                da.extractData(e);
                da.createData(version);
                
                for(int i = 0;i< cnew.getDataList().size();i++){
                    if(cnew.getDataList().get(i).toLowerCase().contains(":" +a.getName().toLowerCase() + ",")
                            && cnew.getDataList().get(i).toLowerCase().contains("_node")
                            && cnew.getDataList().get(i).toLowerCase().contains("default_value")
                            && !cnew.getDataList().get(i).toLowerCase().contains("if")){
                        List<String> temp = new ArrayList<String>();
                        temp.add(da.getData());
                        appendmap.put(i, temp);
                    }
                }
            }
        }
    }
}
