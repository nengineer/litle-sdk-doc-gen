package combiner;

import java.util.List;

public class LineMarkerForRuby implements LineMarker {

    @Override
    public void markLines(int location, ContentCombiner c) {
        // TODO Auto-generated method stub
        int i = location-1;
        while(i>=0 && c.getDataList().get(i).trim().isEmpty()){
            i--;
        }
        if(i>=0 && c.getDataList().get(i).trim().startsWith("#")){
            while(i>0 && c.getDataList().get(i).trim().startsWith("#")){
                c.addToFlaggedLocation(i);
                i--;
            }
        }
    }
}
