package CodeParser;

import static CodeParser.RegexPatterns.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SyntaxValidator {
    private static final Pattern VARIABLE_DECLARATION_PATTERN = Pattern.compile(
            "^\\s*" + FINAL + VAR_TYPE + "\\s+" + VAR_NAME + "(\\s*=\\s*" + ARGUMENT + ")?" +
                    "(\\s*,\\s*" + VAR_NAME + "(\\s*=\\s*" + ARGUMENT + ")?)*" + "\\s*;\\s*$"
    );

    private static final Pattern ASSIGNMENT_PATTERN = Pattern.compile(
            "^\\s*" + VAR_NAME + "\\s*=\\s*" + ARGUMENT + "\\s*;\\s*$"
    );
    private static final Pattern METHOD_DECLARATION_PATTERN = Pattern.compile(
            "^\\s*void\\s+" + METHOD_NAME + "\\s*\\(\\s*" + "(" + FINAL + VAR_TYPE + "\\s+" + VAR_NAME +
                    "(\\s*,\\s*" + FINAL + VAR_TYPE + "\\s+" + VAR_NAME + ")*" + ")?" + "\\s*\\)\\s*\\{\\s*$"
    );

    private static final Pattern METHOD_CALL_PATTERN = Pattern.compile(
            "^\\s*" + METHOD_NAME + "\\s*\\(\\s*" + "(" + ARGUMENT + "(\\s*,\\s*" + ARGUMENT + ")*"
                    + ")?" + "\\s*\\)\\s*\\;\\s*$"
    );

    private static final Pattern IF_WHILE_BLOCK_PATTERN = Pattern.compile(
            "^\\s*(if|while)\\s*\\(\\s*" + ARGUMENT + "(" + LOGICAL_OPS + ARGUMENT + ")*" +
                    "\\s*\\)\\s*\\{\\s*$"
    );

    private static final Pattern RETURN_PATTERN = Pattern.compile("^\\s*return\\s*;\\s*$");
    private static final Pattern CLOSING_BRACKET_PATTERN = Pattern.compile("^\\s*}\\s*$");

    public static final HashMap<Pattern, LineType> patterns = new HashMap<>();

    public void validateCode(ArrayList<Line> code) throws SyntaxException {
        createLineClassificationPatterns();
        for(Line line : code) {
            LineType lineType = checkLine(line);
            line.setLineType(lineType);
        }
    }

    private LineType checkLine(Line line) throws SyntaxException {
        String lineContent = line.getContent();
        for(Map.Entry<Pattern, LineType> entry: patterns.entrySet()){
            Pattern p = entry.getKey();
            Matcher m = p.matcher(lineContent);
            if(m.matches()){
                return entry.getValue();
            }
        }
        throw new SyntaxException(line.getLineIndex(), "invalid syntax");
    }

    private void createLineClassificationPatterns(){
        patterns.put(VARIABLE_DECLARATION_PATTERN, LineType.VARIABLE_DECLARATION);
        patterns.put(METHOD_DECLARATION_PATTERN, LineType.METHOD_DECLARATION);
        patterns.put(IF_WHILE_BLOCK_PATTERN, LineType.IF_WHILE_BLOCK);
        patterns.put(RETURN_PATTERN, LineType.RETURN);
        patterns.put(CLOSING_BRACKET_PATTERN, LineType.CLOSING_BRACKET);
        patterns.put(ASSIGNMENT_PATTERN, LineType.ASSIGNMENT);
        patterns.put(METHOD_CALL_PATTERN, LineType.METHOD_CALL);
    }
}