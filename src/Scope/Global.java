package Scope;

import CodeParser.Line;
import CodeParser.LineType;
import LineParsing.MethodDeclarationParsing;
import LineParsing.MethodParameter;
import Scope.Validation.ScopeValidatorStrategy;
import Scope.Validation.ValidationStrategys.ErrorStrategy;
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
        ScopeValidatorStrategy validator = new ScopeValidatorStrategy();
        validator.setStartIndex(0);
        validator.addStrategy(LineType.METHOD_DECLARATION, new MethodDeclarationParsing().getValidationStrategy());

        ErrorStrategy errorStrategy = new ErrorStrategy("Illegal type in global scope");
        validator.addStrategy(LineType.IF_WHILE_BLOCK, errorStrategy);
        validator.addStrategy(LineType.METHOD_CALL, errorStrategy);
        validator.addStrategy(LineType.RETURN, errorStrategy);
        validator.addStrategy(LineType.CLOSING_BRACKET, errorStrategy);

        validator.validate(this);
    }

    private void secondPass() throws IllegalCodeException {
        ScopeValidatorStrategy validation = new ScopeValidatorStrategy();

        for (Method method : methods.values()) {
            validation.validate(method);
        }
    }

}