package Locaters;

import java.util.List;
import java.util.Map;


public class StringLocaterForRuby implements StringLocater {

    List<String> contentlist;
    Map<Integer, String> locationmap;
    
    
    @Override
    public void findLocations(String keyword) {
        // TODO Auto-generated method stub

        
    }
    
    public void setContentList(List<String> clist){
        contentlist = clist;
    }
    
    
    public List<String> getContentList(){
        return contentlist;
    }
    
    
    public void setLocationMap(Map<Integer, String> cmap){
        locationmap = cmap;
    }
    
    public Map<Integer, String> getLocationMap(){
        return locationmap;
    }
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
