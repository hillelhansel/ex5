package scope;

import syntax.Line;
import syntax.LineType;
import scope.LineHandlers.MethodParameter;
import object.SObject;
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
    public void validateLine(Line line) throws ScopeException {
        if (line.getLineType() == LineType.METHOD_DECLARATION) {
            throw new ScopeException("Nested methods not allowed");
        }
    }

    @Override
    public void validateScopeEnd() throws ScopeException {
        Line last = lines.get(lines.size() - 2);
        if (last.getLineType() != LineType.RETURN) {
            throw new ScopeException("Method must end with return");
        }
    }
}