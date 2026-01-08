package Validation.Strategys;

import CodeParser.Line;
import LineParsing.AssignmentDeclarationParsing;
import LineParsing.Var;
import Scopes.Scope;
import Scopes.ScopeException;
import Variables.SObject;
import main.IllegalCodeException;

import java.util.ArrayList;

public class AssignmentStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        AssignmentDeclarationParsing parser = new AssignmentDeclarationParsing(line);
        ArrayList<Var> assignmentVariable = parser.getParams();

        for (Var var : assignmentVariable) {
            String varName = var.getName();
            String valueExpression = var.getValue();
            SObject targetVar = scope.getObject(varName);

            if (targetVar == null) {
                throw new ScopeException(line.getLineIndex() + ": Variable '" + varName + "' is not defined");
            }

            if (targetVar.isFinal()) {
                throw new ScopeException(line.getLineIndex() + ": Cannot modify final variable '" + varName + "'");
            }

            SObject sourceVar = scope.getObject(valueExpression);
            if (sourceVar != null) {
                if (!sourceVar.isInitialized()) {
                    throw new ScopeException(line.getLineIndex() + ": Variable '" + valueExpression + "' is not initialized");
                }

                if (sourceVar.getVarType() != targetVar.getVarType()) {
                    throw new ScopeException(line.getLineIndex() + ": cannot assign " + sourceVar.getVarType() + " to " + targetVar.getVarType());
                }
            }
            else {
                if (!isLiteralMatchingType(valueExpression, targetVar.getVarType())) {
                    throw new ScopeException(line.getLineIndex() + ": value '" + valueExpression + "' is not valid for type " + targetVar.getVarType());
                }
            }
            targetVar.setIsInitialized(true);
        }
        return 1;
    }
}

