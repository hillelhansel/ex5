package Scope;

import CodeParser.Line;
import CodeParser.LineType;
import LineParsing.MethodParameter;
import Variables.SObject;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;

public class Method extends Scope{
    private final ArrayList<MethodParameter> methodParameters;

    public Method(Scope parent, ArrayList<Line> lines, ArrayList<MethodParameter> methodParameters) throws IllegalCodeException {
        super(parent, lines,  ScopeType.METHOD);
        this.methodParameters = methodParameters;

        for (MethodParameter methodParameter : methodParameters) {
            SObject paramObject = new SObject(methodParameter.getName(), methodParameter.isFinal(),
                    methodParameter.getType(), methodParameter.getType());
            addVariable(paramObject, methodParameter.getName());
        }
    }

    public ArrayList<MethodParameter> getMethodParams() {
        return methodParameters;
    }

    @Override
    public void validateScopeEnd() throws ScopeException {
        if (lines.isEmpty()){
            throw new ScopeException("Method cannot be empty");
        }

        Line lastLine = lines.get(lines.size() - 2);
        if (lastLine.getLineType() != LineType.RETURN) {
            throw new ScopeException("Method must end with return");
        }
    }
}