package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import LineParsing.IfWhileParsing;
import Scope.Block;
import Scope.Scope;
import Scope.ScopeException;
import Scope.Validation.ScopeValidator;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;

public class IfWhileStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        IfWhileParsing parser = new IfWhileParsing(line);
        ArrayList<String> conditions = parser.getParameters();

        for (String condition : conditions) {
            try {
                validateValueByType(scope, condition, VarTypes.SBOOLEAN);
            }
            catch (ScopeException e) {
                try {
                    validateValueByType(scope, condition, VarTypes.SDOUBLE);
                }
                catch (ScopeException e2) {
                    validateValueByType(scope, condition, VarTypes.SINT);
                }
            }
        }

        Block newBlock = scope.addIfWhile(index);
        ScopeValidator innerValidator = new ScopeValidator();
        innerValidator.validate(newBlock);

        return newBlock.getLines().size();
    }
}