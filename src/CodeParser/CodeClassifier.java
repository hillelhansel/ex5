package CodeParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CodeClassifier {
    private static final String SPACE = "\\s*";
    private static final String ANYTHING = ".*";
    private static final String FINAL = "(final\\s+)?";
    private static final String VAR_TYPE = "(int|double|String|boolean|char)\\b";
    private static final String VAR_DECLARATION = "^" + SPACE + FINAL + VAR_TYPE + ANYTHING;
    private static final String METHOD_DECLARATION = "^" + SPACE + "void\\b" + ANYTHING;
    private static final String RETURN = "^" + SPACE + "return\\b" + ANYTHING;
    private static final String IF_WHILE = "^" + SPACE + "(if|while)\\b" + ANYTHING;
    private static final String CLOSING_BLOCK = "^" + SPACE + "\\}" + ANYTHING;
    private static final String ASSIGNMENT = "^" + SPACE + "[a-zA-Z_]\\w*\\s*=.*";
    private static final String METHOD_CALL = "^" + SPACE + "[a-zA-Z_]\\w*\\s*\\(.*";

    public static final LinkedHashMap<Pattern, LineType> patterns = new LinkedHashMap<>();

    public void classifyCode(ArrayList<Line> code) throws UnSupportedArgumentException {
        createLineClassificationPatterns();
        for(Line line : code) {
            line.setLineType(classifyLine(line.getContent()));
            if(line.getLineType() == LineType.INVALID) {
                throw new UnSupportedArgumentException(line.getLineIndex());
            }
        }
    }

    private LineType classifyLine(String line) {
        for(Map.Entry<Pattern, LineType> entry: patterns.entrySet()){
            Pattern p = entry.getKey();
            Matcher m = p.matcher(line);
            if(m.matches()){
                return entry.getValue();
            }
        }
        return LineType.INVALID;
    }

    private void createLineClassificationPatterns(){
        patterns.put(Pattern.compile(VAR_DECLARATION), LineType.VARIABLE_DECLARATION);
        patterns.put(Pattern.compile(METHOD_DECLARATION), LineType.METHOD_DECLARATION);
        patterns.put(Pattern.compile(IF_WHILE), LineType.IF_WHILE_BLOCK);
        patterns.put(Pattern.compile(RETURN), LineType.RETURN);
        patterns.put(Pattern.compile(CLOSING_BLOCK), LineType.CLOSING_BRACKET);
        patterns.put(Pattern.compile(ASSIGNMENT), LineType.ASSIGNMENT);
        patterns.put(Pattern.compile(METHOD_CALL), LineType.METHOD_CALL);
    }
}