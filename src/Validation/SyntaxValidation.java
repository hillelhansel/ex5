package Validation;

import Main.Line;
import Main.LineType;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SyntaxValidation {
    private static final String VAR_NAME = "(?:[a-zA-Z][a-zA-Z\\d_]*|_[a-zA-Z][a-zA-Z\\d_]*)";
    private static final String TYPE = "(int|double|String|boolean|char)";

    private static final Pattern VARIABLE_DECLARATION_PATTERN = Pattern.compile(
            "^\\s*(final\\s+)?" + TYPE + "\\s+" + VAR_NAME + "(\\s*=\\s*[^,;]+)?" + "(\\s*,\\s*"
                    + VAR_NAME + "(\\s*=\\s*[^,;]+)?)*" + "\\s*;\\s*$"
    );
    private static final Pattern ASSIGNMENT_PATTERN = Pattern.compile(
            "^\\s*" + VAR_NAME + "\\s*=\\s*[^,;]+\\s*;\\s*$"
    );
    private static final Pattern METHOD_DECLARATION_PATTERN = Pattern.compile(
            "^\\s*void\\s+" + VAR_NAME + "\\s*\\(\\s*" + "(" + TYPE + "\\s+" + VAR_NAME + "(\\s*,\\s*"
                    + TYPE + "\\s+" + VAR_NAME + ")*" + ")?" + "\\s*\\)\\s*\\{\\s*$"
    );
    private static final Pattern METHOD_CALL_PATTERN = Pattern.compile(
            "^\\s*" + VAR_NAME + "\\s*\\(\\s*" + "(" + TYPE + "\\s+" + VAR_NAME + "(\\s*,\\s*"
                    + TYPE + "\\s+" + VAR_NAME + ")*" + ")?" + "\\s*\\)\\s*\\;\\s*$"
    );
    private static final Pattern IF_WHILE_BLOCK_PATTERN = Pattern.compile(
            "^\\s*(if|while)\\s*\\(\\s*" + "(\\s+" + VAR_NAME + "(\\s*,\\s*"
                    + TYPE + "\\s+" + VAR_NAME + ")*" + ")?" + "\\s*\\)\\s*\\}\\s*$"
    );

    private static final Pattern RETURN_PATTERN = Pattern.compile("^\\s*return\\s*;\\s*$");
    private static final Pattern CLOSING_BRACKET_PATTERN = Pattern.compile("^\\s*}\\s*$");

    public void validateSynatx(ArrayList<Line> code) throws SyntaxException {
        for (Line line : code) {
            String content = line.getContent();
            LineType lineType = line.getLineType();
            if (!isValid(content, lineType)) {
                //todo
                throw new SyntaxException(line.getLineIndex(), "invalid syntax");
            }
        }
    }

    private boolean isValid(String line, LineType lineType) {
        switch (lineType) {
            case VARIABLE_DECLARATION:
                return VARIABLE_DECLARATION_PATTERN.matcher(line).matches();
            case ASSIGNMENT:
                return ASSIGNMENT_PATTERN.matcher(line).matches();
            case METHOD_DECLARATION:
                return METHOD_DECLARATION_PATTERN.matcher(line).matches();
            case METHOD_CALL:
                return METHOD_CALL_PATTERN.matcher(line).matches();
            case IF_WHILE_BLOCK:
                return IF_WHILE_BLOCK_PATTERN.matcher(line).matches();
            case RETURN:
                return RETURN_PATTERN.matcher(line).matches();
            case CLOSING_BRACKET:
                return CLOSING_BRACKET_PATTERN.matcher(line).matches();

            default:
                return false;
        }
    }
}