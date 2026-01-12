package Scope;

import CodeParser.Line;
import Scope.LineHandlers.MethodParameter;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.HashMap;


public class Global extends Scope {
    private final HashMap<String, Method> methods = new HashMap<>();

    public Global(Scope parent, ArrayList<Line> lines) throws IllegalCodeException {
        super(parent, lines, ScopeType.GLOBAL);
        firstPass();
        secondPass();
    }

    public HashMap<String, Method> getMethods() {
        return methods;
    }

    public int addMethod(String methodName, ArrayList<MethodParameter> methodParameters, int index) throws IllegalCodeException {
        int methodLength = scopeLength(lines, index);

        ArrayList<Line> methodLines = new ArrayList<>(lines.subList(index, index + methodLength));

        methods.put(methodName, new Method(this, methodLines, methodParameters));

        return methodLength;
    }

    private void firstPass() throws IllegalCodeException {
        ScopeValidator validation = new ScopeValidator();
        validation.validate(this);
    }

    @Override
    public void validateLine(Line line) throws ScopeException {
        switch (line.getLineType()) {
            case METHOD_DECLARATION:
            case VARIABLE_DECLARATION:
            case ASSIGNMENT:
                return;
            default:
                throw new ScopeException("Illegal line in global scope");
        }
    }

    private void secondPass() throws IllegalCodeException {
        ScopeValidator validation = new ScopeValidator();

        for (Method method : methods.values()) {
            validation.validate(method);
        }
    }

}