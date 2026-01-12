package CodeParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static CodeParser.RegexPatterns.*;

public class SyntaxValidator {
    private final HashMap<Pattern, LineType> patterns = new HashMap<>();

    public SyntaxValidator() {
        patterns.put(VARIABLE_DECLARATION_PATTERN, LineType.VARIABLE_DECLARATION);
        patterns.put(METHOD_DECLARATION_PATTERN, LineType.METHOD_DECLARATION);
        patterns.put(IF_WHILE_BLOCK_PATTERN, LineType.IF_WHILE_BLOCK);
        patterns.put(RETURN_PATTERN, LineType.RETURN);
        patterns.put(CLOSING_BRACKET_PATTERN, LineType.CLOSING_BRACKET);
        patterns.put(ASSIGNMENT_PATTERN, LineType.ASSIGNMENT);
        patterns.put(METHOD_CALL_PATTERN, LineType.METHOD_CALL);
    }

    public void validateCode(ArrayList<Line> code) throws SyntaxException {
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
        throw new SyntaxException(line.getLineIndex(), "Invalid syntax");
    }
}