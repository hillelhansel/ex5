package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import Scope.Scope;
import Scope.ScopeException;
import main.IllegalCodeException;

public class MethodDeclarationStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        throw new ScopeException("Nested methods are not allowed");
    }
}