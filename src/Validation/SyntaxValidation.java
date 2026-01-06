package Validation;

import CodeParser.Line;
import CodeParser.LineType;

import static CodeParser.RegexPatterns.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SyntaxValidation {
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


    public void validateSyntax(ArrayList<Line> code) throws SyntaxException {
        for (Line line : code) {
            String content = line.getContent();
            LineType lineType = line.getLineType();

            if (!isValid(content, lineType)) {
                throw new SyntaxException(line.getLineIndex());
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