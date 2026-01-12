package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import Scope.Scope;
import Scope.ScopeException;
import Scope.Validation.ValidationStrategy;
import main.IllegalCodeException;

public class ClosingBracketStrategy implements ValidationStrategy {

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        if(index < scope.getLines().size() - 1) {
            throw new ScopeException("closing bracket can only be in the last scope line");
        }
        return 1;
    }
}
