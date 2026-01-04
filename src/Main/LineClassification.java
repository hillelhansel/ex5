package Main;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LineClassification {
    public LineType classifyLine(String line) {
        for(Map.Entry<Pattern, LineType> entry: Regex.patterns.entrySet()){
            Pattern p = entry.getKey();
            Matcher m = p.matcher(line);
            if(m.matches()){
                return entry.getValue();
            }
        }
        return LineType.INVALID;
    }
}