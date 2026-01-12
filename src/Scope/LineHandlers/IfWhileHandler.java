package Scope.LineHandlers;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import Scope.Scope;
import Scope.LineHandler;
import Variables.VarTypes;
import main.IllegalCodeException;
import Scope.ScopeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class IfWhileHandler implements LineHandler {
    private ArrayList<String> parameters;
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    public void parse(String content) {
        String insideBrackets = lineParsing.extractContentInsideBrackets(content);

        String[] parts = insideBrackets.split(RegexPatterns.LOGICAL_OPS);

        this.parameters = Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        parse(line.getContent());
        ArrayList<String> conditions = parameters;

        for (String condition : conditions) {
            VarTypes type = scope.resolveExpressionType(condition);

            if(!type.isTypeCompatible(type, VarTypes.SBOOLEAN)){
                throw new ScopeException("Condition " + condition + " is not a boolean");
            }
        }

        return scope.openIfWhileBlock(index);
    }

}