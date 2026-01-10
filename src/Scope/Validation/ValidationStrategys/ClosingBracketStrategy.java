package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import Scope.Scope;
import Scope.ScopeException;
import main.IllegalCodeException;

public class ClosingBracketStrategy extends BaseStrategy {

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        if(index < scope.getLines().size() - 1) {
            throw new ScopeException(index, "closing bracket can only be in the last scope line");
        }
        return 1;
    }
}
