package mainApp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import combiner.ContentCombiner;

public class Test {

		public static void main(String[] args){
			
			Map<String, Integer> tmap = new HashMap<String, Integer>();
			
			tmap.put("prepaidcardtype", 0);
			tmap.put("type", 0);
			
			String fileAdd = "/usr/local/litle-home/vchouhan/Desktop/testarena/testarena1/testarena2/litle-sdk-for-java/generated/com/litle/sdk/generate/EnhancedAuthResponse.java";

			System.out.print("processing file : " + fileAdd);
			ContentCombiner ctest = new ContentCombiner(new ArrayList<Integer>());
			ctest.storeContent(new File(fileAdd));
			for(String cline : ctest.getDataList()){
				if(cline.trim().startsWith("public")){
					for(Entry<String, Integer> e : tmap.entrySet()){
						if(cline.toLowerCase().contains("set" + e.getKey().toLowerCase() + "(")
							||cline.toLowerCase().contains("get" + e.getKey().toLowerCase() + "(")
							||cline.toLowerCase().contains("is" + e.getKey().toLowerCase() + "(")){
								ctest.getLocations().add(ctest.getDataList().indexOf(cline)+1);
								e.setValue(e.getValue() + 1);
							}
					}
				}
			}
			
			String test1 = "public FundingSourceTypeEnum getType() {";
			
			String test2 = "gettype(";
			
			if(test1.trim().toLowerCase().contains(test2.toLowerCase())){
				System.out.println("it is working");
			}
			
			System.out.println(test1.toLowerCase());
			for(int i : ctest.getLocations()){
				
				System.out.println(i);
				
				
			}
			//System.out.println(" at " + ctest.getLocations().size() + " number of places");
			ctest.processContent();
			ctest.removeFlagged(new File(fileAdd));
			ctest.showContent(new File(fileAdd));
			
		}
}
