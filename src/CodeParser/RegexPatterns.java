package CodeParser;

public class RegexPatterns {
    public static final String VAR_NAME = "(?:[a-zA-Z][a-zA-Z\\d_]*|_[a-zA-Z][a-zA-Z\\d_]*)";
    public static final String METHOD_NAME = "([a-zA-Z][a-zA-Z\\d_]*)";
    public static final String VAR_TYPE = "(int|double|String|boolean|char)\\b";
    public static final String INT = "[+-]?\\d+";
    public static final String DOUBLE = "[+-]?\\d+(\\.\\d+)?";
    public static final String STRING = "\"[^\"]*\"";
    public static final String BOOLEAN = "(true|false|" + DOUBLE + "|" + INT + ")";
    public static final String CHAR = "'[^']'";
    public static final String ARGUMENT = "(" + INT + "|" + DOUBLE + "|" + STRING + "|" + CHAR + "|" +
            BOOLEAN + "|" + VAR_NAME + ")";

    public static final String FINAL = "(final\\s+)?";
    public static final String LOGICAL_OPS = "\\s*(\\|\\||&&)\\s*";
}
