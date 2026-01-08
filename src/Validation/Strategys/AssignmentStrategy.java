package Validation.Strategys;

import CodeParser.Line;
import LineParsing.AssignmentDeclarationParsing;
import LineParsing.Var;
import Scopes.Scope;
import Scopes.ScopeException;
import Variables.SObject;
import Variables.VarTypes;
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
            SObject targetVar = checkVarExist(line, scope, varName);
            if (targetVar.isFinal()) {
                throw new ScopeException(": Cannot modify final variable '" + varName + "'");
            }

            SObject sourceVar = scope.getObject(valueExpression);
            VarTypes targetVarType = sourceVar.getVarType();
            VarTypes sourceVarType = sourceVar.getVarType();

            if (sourceVar != null) {
                if (!sourceVar.isInitialized()) {
                    throw new ScopeException(": Variable '" + valueExpression + "' is not initialized");
                }


                if (sourceVarType != targetVarType) {
                    throw new ScopeException(": cannot assign " + sourceVarType + " to " + targetVarType);
                }
            }
            else {
                if (!isLiteralMatchingType(valueExpression, targetVarType)) {
                    throw new ScopeException(": value '" + valueExpression + "' is not valid for type " + targetVarType);
                }
            }
            targetVar.setIsInitialized(true);
        }
        return 1;
    }
}

