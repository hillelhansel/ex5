package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import LineParsing.AssignmentParsing;
import LineParsing.Var;
import Scope.Scope;
import Scope.ScopeException;
import Variables.SObject;
import Variables.VarTypes;
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

            SObject targetVar = scope.getObject(varName);
            if (targetVar == null) {
                throw new ScopeException(": Variable '" + varName + "' is not defined");
            }

            VarTypes incomingType = getTypeOfExpression(scope, valueExpression);

            try {
                targetVar.tryAssign(incomingType);
            }
            catch (Exception e) {
                throw new ScopeException(e.getMessage());
            }
        }
        return 1;
    }
}