package formatter;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

public class StringFormatter {

    public StringFormatter(){}

    public ArrayList<String> splitSentences(String source){
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(source);
        ArrayList<String> result = new ArrayList<String>();
        int start = iterator.first();
        for (int end = iterator.next();
            end != BreakIterator.DONE;
            start = end, end = iterator.next()) {
          result.add(source.substring(start,end));
        }
        return result;
    }

}
