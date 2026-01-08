package Validation.Strategys;

import CodeParser.Line;
import LineParsing.IfWhileParsing;
import Scopes.Scope;
import main.IllegalCodeException;

import java.util.ArrayList;

public class IfWhileStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        IfWhileParsing ifWhileParsing = new IfWhileParsing(line);
        ArrayList<String> blockParam = ifWhileParsing.getBlockParameters();

        for(String param : blockParam) {
            checkVarExist(line, scope, param);
        }

        return scope.addIfWhile(index);
    }
}
