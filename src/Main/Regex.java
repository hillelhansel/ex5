package Main;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Regex {
    private static final String VAR_DECL_PATTERN = "^\\s*(final\\s+)?(int|double|String|boolean|char)\\s+.*";
    private static final String ASSIGNMENT_PATTERN = "^\\s*[a-zA-Z_]\\w*\\s*=.*";
    private static final String METHOD_DECL_PATTERN = "^\\s*void\\s+[a-zA-Z]\\w*\\s*\\(.*\\)\\s*\\{\\s*$";
    private static final String METHOD_CALL_PATTERN = "^\\s*[a-zA-Z]\\w*\\s*\\(.*\\)\\s*;\\s*$";
    private static final String IF_WHILE_PATTERN = "^\\s*(if|while)\\s*\\(.*\\)\\s*\\{\\s*$";
    private static final String RETURN_PATTERN = "^\\s*return\\s*;\\s*$";
    private static final String CLOSING_BLOCK_PATTERN = "^\\s*\\}\\s*$";
    private static final String COMMENT_PATTERN = "^\\s*//.*";
    private static final String EMPTY_PATTERN = "^\\s*$";

    public static final LinkedHashMap<Pattern, LineType> patterns = new LinkedHashMap<>();

    public Regex() {
        createLineClassificationPatterns();
    }

    private void createLineClassificationPatterns(){
        patterns.put(Pattern.compile(VAR_DECL_PATTERN), LineType.VARIABLE_DECLARATION);
        patterns.put(Pattern.compile(ASSIGNMENT_PATTERN), LineType.ASSIGNMENT);
        patterns.put(Pattern.compile(METHOD_DECL_PATTERN), LineType.ASSIGNMENT);
        patterns.put(Pattern.compile(METHOD_CALL_PATTERN), LineType.METHOD_DECLARATION);
        patterns.put(Pattern.compile(IF_WHILE_PATTERN), LineType.METHOD_CALL);
        patterns.put(Pattern.compile(RETURN_PATTERN), LineType.IF_WHILE_BLOCK);
        patterns.put(Pattern.compile(CLOSING_BLOCK_PATTERN), LineType.RETURN);
        patterns.put(Pattern.compile(CLOSING_BLOCK_PATTERN), LineType.CLOSING_BRACKET);
        patterns.put(Pattern.compile(COMMENT_PATTERN), LineType.COMMENT);
        patterns.put(Pattern.compile(EMPTY_PATTERN), LineType.EMPTY_LINE);
    }
}
