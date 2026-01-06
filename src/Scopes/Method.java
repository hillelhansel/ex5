package Scopes;

import CodeParser.Line;
import LineParsing.MethodParameter;
import Variables.InvalidValueException;

import java.util.ArrayList;

public class Method extends Scope{
    private final ArrayList<MethodParameter> methodParameters;
    public Method(Scope parent, ArrayList<Line> lines, ArrayList<MethodParameter> methodParameters) throws InvalidValueException {
        super(parent, lines);
        this.methodParameters = methodParameters;
    }

    public ArrayList<MethodParameter> getMethodParams() {
        return methodParameters;
    }
}
