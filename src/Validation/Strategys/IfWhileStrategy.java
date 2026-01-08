package Validation.Strategys;

import CodeParser.Line;
import LineParsing.IfWhileParsing;
import Scopes.Scope;
import main.IllegalCodeException;

public class IfWhileStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope) throws IllegalCodeException {
        IfWhileParsing ifWhileParsing = new IfWhileParsing(line);
        return scope.addIfWhile(ifWhileParsing);
    }
}
