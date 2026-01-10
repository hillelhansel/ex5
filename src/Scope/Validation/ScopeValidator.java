package Scope.Validation;

import CodeParser.Line;
import CodeParser.LineType;
import Scope.Scope;
import Scope.Validation.ValidationStrategys.*;
import main.IllegalCodeException;
import Scope.ScopeException;

import java.util.ArrayList;
import java.util.HashMap;

public class ScopeValidator {
    private final HashMap<LineType, ValidationStrategy> strategies = new HashMap<>();

    public ScopeValidator() {
        strategies.put(LineType.ASSIGNMENT, new AssignmentStrategy());
        strategies.put(LineType.VARIABLE_DECLARATION, new VarDeclarationStrategy());
        strategies.put(LineType.IF_WHILE_BLOCK, new IfWhileStrategy());
        strategies.put(LineType.METHOD_CALL, new MethodCallStrategy());
        strategies.put(LineType.METHOD_DECLARATION, new MethodDeclarationStrategy());
        strategies.put(LineType.CLOSING_BRACKET, new ClosingBracketStrategy());
    }

    public void validate(Scope scope) throws IllegalCodeException {
        ArrayList<Line> scopeLines = scope.getLines();
        for (int index = 1; index < scopeLines.size(); index++) {
            Line line = scopeLines.get(index);

            ValidationStrategy strategy = strategies.get(line.getLineType());
            if (strategy != null) {
                try {
                    int linesProcessed = strategy.validate(line, scope, index);
                    index += linesProcessed - 1;
                }
                catch (IllegalCodeException e) {
                    if (e.getMessage().startsWith("Error in line")) {
                        throw e;
                    }
                    throw new ScopeException(line.getLineIndex(), e.getMessage());
                }
            }
        }
        scope.validateScopeEnd();
    }
}
