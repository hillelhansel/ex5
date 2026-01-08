package Scope.Validation;

import CodeParser.Line;
import CodeParser.LineType;
import Scope.Scope;
import Scope.Validation.ValidationStrategys.AssignmentStrategy;
import Scope.Validation.ValidationStrategys.IfWhileStrategy;
import Scope.Validation.ValidationStrategys.MethodCallStrategy;
import Scope.Validation.ValidationStrategys.VarDeclarationStrategy;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.HashMap;

public class ScopeValidator {
    private final HashMap<LineType, ValidationStrategy> strategies = new HashMap<>();

    public ScopeValidator() {
        strategies.put(LineType.ASSIGNMENT, new AssignmentStrategy());
        strategies.put(LineType.VARIABLE_DECLARATION, new VarDeclarationStrategy());
        strategies.put(LineType.IF_WHILE_BLOCK, new IfWhileStrategy());
        strategies.put(LineType.METHOD_CALL, new MethodCallStrategy());
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
                    throw new IllegalCodeException(line.getLineIndex() + e.getMessage());
                }
            }
        }
        scope.validateScopeEnd();
    }
}
