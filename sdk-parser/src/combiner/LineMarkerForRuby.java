package combiner;

public class LineMarkerForRuby implements LineMarker {

    @Override
    public void markLines(int location, ContentCombiner c) {
        // TODO Auto-generated method stub
        
        for(int i = 0; i< c.getDataList().size();i++){
            if(c.getDataList().get(i).trim().startsWith("#")){
                c.getFlaggedLocations().add(i);
            }
        }
        
    }

}
