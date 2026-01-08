package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import LineParsing.Var;
import LineParsing.VarDeclarationParsing;
import Scope.Scope;
import Variables.SObject;
import Variables.VariableException;
import main.IllegalCodeException;

public class VarDeclarationStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        VarDeclarationParsing parser = new VarDeclarationParsing(line);
        boolean isFinal = parser.isFinal();

        for (Var var : parser.getVariables()) {
            if (isFinal && var.getValue() == null) {
                throw new VariableException("Final variable '" + var.getName() + "' must be initialized");
            }

            if (var.getValue() != null) {
                validateValueByType(scope, var.getValue(), parser.getType());
            }

            SObject sObject = new SObject(var.getName(), isFinal, parser.getType(), var.getValue());
            scope.addVariable(sObject, var.getName());
        }

        return 1;
    }
}