package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import LineParsing.Var;
import LineParsing.VarDeclarationParsing;
import Scope.Scope;
import Variables.SObject;
import main.IllegalCodeException;

public class VarDeclarationStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        VarDeclarationParsing parser = new VarDeclarationParsing(line);
        boolean isFinal = parser.isFinal();

        for (Var var : parser.getVariables()) {
            SObject sObject = new SObject(var.getName(), isFinal, parser.getType(), var.getValue());
            scope.addVariable(sObject, var.getName());
        }
        return 1;
    }
}