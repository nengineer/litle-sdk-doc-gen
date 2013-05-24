package combiner;

import java.util.List;

public class LineMarkerForJava implements LineMarker{

	@Override
	public void markLines(int location, ContentCombiner c) {
		List<String> dlist = c.getDataList();
		int i = location-1;
		while(i>=0 && dlist.get(i).trim().isEmpty()){
			i--;
		}
		if(i>=0 && dlist.get(i).trim().endsWith("*/")){
			while(i>0 && !dlist.get(i).trim().contains("/*")){
				c.addToFlaggedLocation(i);
				i--;
			}
			c.addToFlaggedLocation(i);
		}
	}

}
