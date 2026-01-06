package Scopes;

import CodeParser.Line;
import LineParsing.MethodParameter;
import Variables.SObject;
import Variables.VariableException;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;

public class Method extends Scope{
    private final ArrayList<MethodParameter> methodParameters;
    public Method(Scope parent, ArrayList<Line> lines, ArrayList<MethodParameter> methodParameters) throws IllegalCodeException {
        super(parent, lines);
        this.methodParameters = methodParameters;
        for (MethodParameter methodParameter : methodParameters) {
            String name = methodParameter.getName();
            VarTypes type = methodParameter.getType();
            boolean isFinal = methodParameter.isFinal();
            addVariable(new SObject(name, isFinal, type, null), name);
        }
    }

    public ArrayList<MethodParameter> getMethodParams() {
        return methodParameters;
    }
}
