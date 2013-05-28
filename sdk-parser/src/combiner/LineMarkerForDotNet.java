package combiner;

import java.util.List;

public class LineMarkerForDotNet implements LineMarker {

    private final static String PatternToMatch = "/// <remarks/>";
	@Override
	public void markLines(int location, ContentCombiner c) {
		List<String> dlist = c.getDataList();
		int i = location-1;
		while(i>=0 && dlist.get(i).trim().isEmpty()){
			i--;
		}
		if (i>=0 && dlist.get(i).trim().contains(PatternToMatch)){
				c.addToFlaggedLocation(i);
				i--;
			}
		}
    public String getPatternToMatch() {
        return PatternToMatch;
    }

}
