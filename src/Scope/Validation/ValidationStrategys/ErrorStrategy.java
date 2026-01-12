package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import Scope.Scope;
import Scope.ScopeException;
import Scope.Validation.ValidationStrategy;
import main.IllegalCodeException;

public class ErrorStrategy implements ValidationStrategy {
    String errorMsg;
    public ErrorStrategy(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        throw new ScopeException(errorMsg);
    }
}