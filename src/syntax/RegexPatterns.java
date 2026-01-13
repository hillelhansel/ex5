package syntax;

import java.util.regex.Pattern;

public class RegexPatterns {
    public static final String VAR_NAME = "(_?[a-zA-Z][a-zA-Z\\\\d_]*)";
    public static final String METHOD_NAME = "([a-zA-Z][a-zA-Z\\d_]*)";
    public static final String VAR_TYPE = "(int|double|String|boolean|char)\\b";
    public static final String INT = "[+-]?\\d+";
    public static final String DOUBLE = "[+-]?(\\d+\\.\\d*|\\.\\d+|" + INT + ")";
    public static final String STRING = "\"[^\"]*\"";
    public static final String BOOLEAN = "(true|false|" + DOUBLE + "|" + INT + ")";
    public static final String CHAR = "'[^']'";
    public static final String ARGUMENT = "(" + INT + "|" + DOUBLE + "|" + STRING + "|" + CHAR + "|" +
            BOOLEAN + "|" + VAR_NAME + ")";

    public static final String FINAL = "(final\\s+)?";
    public static final String LOGICAL_OPS = "\\s*(\\|\\||&&)\\s*";

    public static final Pattern HEADER_MATCHER = Pattern.compile("^\\s*" + RegexPatterns.FINAL +
            "(" + RegexPatterns.VAR_TYPE + ")");

    public static final Pattern VARIABLE_DECLARATION_PATTERN = Pattern.compile(
            "^\\s*" + FINAL + VAR_TYPE + "\\s+" + VAR_NAME + "(\\s*=\\s*" + ARGUMENT + ")?" +
                    "(\\s*,\\s*" + VAR_NAME + "(\\s*=\\s*" + ARGUMENT + ")?)*" + "\\s*;\\s*$");

    public static final Pattern ASSIGNMENT_PATTERN = Pattern.compile(
            "^\\s*" + VAR_NAME + "\\s*=\\s*" + ARGUMENT + "\\s*;\\s*$");

    public static final Pattern METHOD_DECLARATION_PATTERN = Pattern.compile(
            "^\\s*void\\s+" + METHOD_NAME + "\\s*\\(\\s*" + "(" + FINAL + VAR_TYPE + "\\s+" +
                    VAR_NAME + "(\\s*,\\s*" + FINAL + VAR_TYPE + "\\s+" + VAR_NAME + ")*" + ")?" +
                    "\\s*\\)\\s*\\{\\s*$");

    public static final Pattern METHOD_CALL_PATTERN = Pattern.compile(
            "^\\s*" + METHOD_NAME + "\\s*\\(\\s*" + "(" + ARGUMENT + "(\\s*,\\s*" + ARGUMENT + ")*"
                    + ")?" + "\\s*\\)\\s*;\\s*$");

    public static final Pattern IF_WHILE_BLOCK_PATTERN = Pattern.compile(
            "^\\s*(if|while)\\s*\\(\\s*" + ARGUMENT + "(" + LOGICAL_OPS + ARGUMENT + ")*" +
                    "\\s*\\)\\s*\\{\\s*$");

    public static final Pattern RETURN_PATTERN = Pattern.compile("^\\s*return\\s*;\\s*$");
    public static final Pattern CLOSING_BRACKET_PATTERN = Pattern.compile("^\\s*}\\s*$");

    private RegexPatterns() {}

}
