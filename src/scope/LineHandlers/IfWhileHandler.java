package scope.LineHandlers;

import main.IllegalCodeException;
import object.ObjectType;
import scope.LineHandler;
import scope.Scope;
import scope.ScopeException;
import syntax.Line;
import syntax.RegexPatterns;

import java.util.ArrayList;

public class IfWhileHandler implements LineHandler {
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        ArrayList<String> conditions = parse(line.getContent());

        for (String condition : conditions) {
            ObjectType type = scope.resolveExpressionType(condition);

            if(!type.isTypeCompatible(type, ObjectType.SBOOLEAN)){
                throw new ScopeException("Condition " + condition + " is not a boolean");
            }
        }

        return scope.openIfWhileBlock(index);
    }

    private ArrayList<String> parse(String content) {
        ArrayList<String> parameters =  new ArrayList<>();
        String insideBrackets = lineParsing.extractContentInsideBrackets(content);

        String[] parts = insideBrackets.split(RegexPatterns.LOGICAL_OPS);

        for (String part : parts) {
            parameters.add(part.trim());
        }
        return parameters;
    }

}