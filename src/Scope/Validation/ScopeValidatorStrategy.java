package Scope.Validation;

import CodeParser.Line;
import CodeParser.LineType;
import LineParsing.AssignmentParsing;
import Scope.Scope;
import Scope.ScopeException;
import Scope.Validation.ValidationStrategys.*;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.HashMap;

public class ScopeValidatorStrategy {
    private final HashMap<LineType, ValidationStrategy> strategies = new HashMap<>();
    private int startingIndex = 1;

    public ScopeValidatorStrategy() {
        strategies.put(LineType.ASSIGNMENT, new AssignmentParsing());
        strategies.put(LineType.VARIABLE_DECLARATION, new VarDeclarationStrategy());
        strategies.put(LineType.IF_WHILE_BLOCK, new IfWhileStrategy());
        strategies.put(LineType.METHOD_CALL, new MethodCallStrategy());
        strategies.put(LineType.METHOD_DECLARATION, new MethodDeclarationStrategy());
        strategies.put(LineType.CLOSING_BRACKET, new ClosingBracketStrategy());
    }

    public void addStrategy(LineType lineType, ValidationStrategy strategy) {
        strategies.put(lineType, strategy);
    }

    public void setStartIndex(int startingIndex) {
        this.startingIndex = startingIndex;
    }

    public void validate(Scope scope) throws IllegalCodeException {
        ArrayList<Line> scopeLines = scope.getLines();
        for (int index = startingIndex; index < scopeLines.size(); index++) {
            Line line = scopeLines.get(index);

            ValidationStrategy strategy = strategies.get(line.getLineType());
            if (strategy != null) {
                try {
                    int linesProcessed = strategy.validate(line, scope, index);
                    index += linesProcessed - 1;
                }
                catch (IllegalCodeException e) {
                    throw new ScopeException(line.getLineIndex(), e.getMessage());
                }
            }
        }
        scope.validateScopeEnd();
    }
}
