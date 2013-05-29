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
        return source.replaceAll("\\(.*\\)","");
    }

    public static void main(String[] args){
        String x = "\"\"(empty)zhen";
        System.out.println(new StringFormatter().trimParenForEnum(x));

        Pattern p2 = Pattern.compile("\\[System\\.Xml\\.Serialization\\.XmlEnumAttribute\\(\""+"MASS AFFLU"+"\"\\)\\]");
        Matcher m2 = p2.matcher("[System.Xml.Serialization.XmlEnumAttribute(\"MASS AFFLU\")]");
        System.out.println(m2.find());
    }


}
