package scope.LineHandlers;

import main.IllegalCodeException;
import object.ObjectType;
import scope.LineHandler;
import scope.Scope;
import scope.ScopeException;
import syntax.Line;
import syntax.RegexPatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class IfWhileHandler implements LineHandler {
    private ArrayList<String> parameters;
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        parse(line.getContent());
        ArrayList<String> conditions = parameters;

        for (String condition : conditions) {
            ObjectType type = scope.resolveExpressionType(condition);

            if(!type.isTypeCompatible(type, ObjectType.SBOOLEAN)){
                throw new ScopeException("Condition " + condition + " is not a boolean");
            }
        }

        return scope.openIfWhileBlock(index);
    }

    private void parse(String content) {
        String insideBrackets = lineParsing.extractContentInsideBrackets(content);

        String[] parts = insideBrackets.split(RegexPatterns.LOGICAL_OPS);

        this.parameters = Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }

}