package Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CodeClassifier {
    public void classifyCode(ArrayList<Line> code) throws UnSupportedArgumentException {
        for(Line line : code) {
            line.setLineType(classifyLine(line.getContent()));
            if(line.getLineType() == LineType.INVALID) {
                throw new UnSupportedArgumentException(line.getLineIndex());
            }
        }
    }

    private LineType classifyLine(String line) {
        for(Map.Entry<Pattern, LineType> entry: RegexClassifier.patterns.entrySet()){
            Pattern p = entry.getKey();
            Matcher m = p.matcher(line);
            if(m.matches()){
                return entry.getValue();
            }
        }
        return LineType.INVALID;
    }
}