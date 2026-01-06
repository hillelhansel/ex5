package Scopes;

import CodeParser.Line;
import LineParsing.MethodParameter;
import Variables.InvalidValueException;
import Variables.VarTypes;

import java.util.ArrayList;

public class Method extends Scope{
    private final ArrayList<MethodParameter> methodParameters;
    public Method(Scope parent, ArrayList<Line> lines, ArrayList<MethodParameter> methodParameters) throws InvalidValueException {
        super(parent, lines);
        this.methodParameters = methodParameters;
        for (MethodParameter methodParameter : methodParameters) {
            String name = methodParameter.getName();
            VarTypes type = methodParameter.getType();
            boolean isFinal = methodParameter.isFinal();
            addVariable(varFactory.getObject(name, isFinal, type, ));
        }
    }

    public ArrayList<MethodParameter> getMethodParams() {
        return methodParameters;
    }
}
