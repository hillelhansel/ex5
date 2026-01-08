package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import LineParsing.VarDeclarationParsing;
import Scope.Scope;
import main.IllegalCodeException;

public class VarDeclarationStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        VarDeclarationParsing parser = new VarDeclarationParsing(line);
        scope.addVariables(parser);
        return 1;
    }
}
