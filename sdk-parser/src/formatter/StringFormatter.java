package formatter;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormatter {

    public StringFormatter(){}

    /**
     * split the description, notes, extra, and enums sentence by sentence
     * @param source
     * @return
     */
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

    public String trimParenForEnum(String source){
        return source.replaceAll("\\(.*\\)","").trim();
    }

}
