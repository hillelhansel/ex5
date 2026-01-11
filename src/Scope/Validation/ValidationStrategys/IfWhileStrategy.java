package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import LineParsing.IfWhileParsing;
import Scope.Block;
import Scope.Scope;
import Scope.ScopeException;
import Scope.Validation.ScopeValidatorStrategy;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;

public class IfWhileStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        IfWhileParsing parser = new IfWhileParsing(line);
        ArrayList<String> conditions = parser.getParameters();

        for (String condition : conditions) {
            VarTypes type = getTypeOfExpression(scope, condition);
            if(!type.isTypeCompatible(type, VarTypes.SBOOLEAN)){
                throw new ScopeException("Condition " + condition + " is not a boolean");
            }
        }

        Block newBlock = scope.addIfWhile(index);
        ScopeValidatorStrategy innerValidator = new ScopeValidatorStrategy();
        innerValidator.validate(newBlock);

        return newBlock.getLines().size();
    }
}