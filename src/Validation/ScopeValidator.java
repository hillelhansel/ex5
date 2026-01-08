package Validation;

import CodeParser.Line;
import CodeParser.LineType;
import LineParsing.*;
import Scopes.Scope;
import Validation.Strategys.AssignmentStrategy;
import Validation.Strategys.IfWhileStrategy;
import Validation.Strategys.MethodCallStrategy;
import Validation.Strategys.VarDeclarationStrategy;
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
        ArrayList<Line> lines = scope.getLines();
        for (int index = 1; index < lines.size(); index++) {
            Line line = lines.get(index);

            ValidationStrategy strategy = strategies.get(line.getLineType());
            if (strategy != null) {
                int linesProcessed = strategy.validate(line, scope, index);
                index += linesProcessed - 1;
            }
        }
    }
}
