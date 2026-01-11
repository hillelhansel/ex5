package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import LineParsing.MethodDeclarationParsing;
import Scope.Scope;
import main.IllegalCodeException;
import Scope.ScopeType;
import Scope.Global;
import Scope.ScopeException;

public class MethodDeclarationStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        MethodDeclarationParsing methodDeclarationParsing = new MethodDeclarationParsing(line);
        if (scope.getScopeType() != ScopeType.GLOBAL) {
            throw new ScopeException("Global scope not found");
        }
        Global global = (Global) scope;
        return global.addMethods(methodDeclarationParsing, index);
    }
}
