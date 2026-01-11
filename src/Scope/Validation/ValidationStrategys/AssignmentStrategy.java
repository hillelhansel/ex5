package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import LineParsing.AssignmentParsing;
import LineParsing.Var;
import Scope.Scope;
import Scope.ScopeException;
import Variables.SObject;
import main.IllegalCodeException;

import java.util.ArrayList;

public class AssignmentStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        AssignmentParsing parser = new AssignmentParsing(line);
        ArrayList<Var> assignmentVariable = parser.getParams();

        for (Var var : assignmentVariable) {
            String varName = var.getName();
            String valueExpression = var.getValue();

            SObject targetVar = checkVarExist(scope, varName);

            if (targetVar.isFinal()) {
                throw new ScopeException(": Cannot modify final variable '" + varName + "'");
            }
            validateValueByType(scope, valueExpression, targetVar.getVarType());

            targetVar.setIsInitialized(true);
        }
        return 1;
    }
}