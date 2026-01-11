package Scope;

import CodeParser.Line;
import LineParsing.MethodDeclarationParsing;
import LineParsing.MethodParameter;
import Scope.Validation.ScopeValidator;
import Scope.Validation.ValidationStrategys.AssignmentStrategy;
import Scope.Validation.ValidationStrategys.VarDeclarationStrategy;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.HashMap;


public class Global extends Scope {
    private final HashMap<String, Method> methods = new HashMap<>();

    public Global(Scope parent, ArrayList<Line> lines) throws IllegalCodeException {
        super(parent, lines, ScopeType.GLOBAL);
        firstPass(lines);
        secondPass();
    }

    public HashMap<String, Method> getMethods() {
        return methods;
    }

    private void firstPass(ArrayList<Line> lines) throws IllegalCodeException {
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            try {
                switch (line.getLineType()) {
                    case METHOD_DECLARATION:
                        MethodDeclarationParsing methodDeclarationParsing = new MethodDeclarationParsing(line);
                        int methodLength = addMethods(methodDeclarationParsing, i);
                        i += methodLength - 1;
                        break;

                    case VARIABLE_DECLARATION:
                        new VarDeclarationStrategy().validate(line, this, i);
                        break;

                    case ASSIGNMENT:
                        new AssignmentStrategy().validate(line, this, i);
                        break;

                    case CLOSING_BRACKET, IF_WHILE_BLOCK, METHOD_CALL, RETURN:
                        throw new ScopeException(line.getLineIndex(), "Illegal type in global scope");

                    default:
                        break;
                }
            }
            catch (IllegalCodeException e) {
                throw new IllegalCodeException(line.getLineIndex(), e.getMessage());
            }
        }
    }

    private void secondPass() throws IllegalCodeException {
        ScopeValidator validation = new ScopeValidator();

        for (Method method : methods.values()) {
            validation.validate(method);
        }
    }

    private int addMethods(MethodDeclarationParsing methodDeclarationParsing, int index) throws IllegalCodeException {
        String methodName = methodDeclarationParsing.getMethodName();
        int methodLength = scopeLength(lines, index);

        ArrayList<Line> methodLines = new ArrayList<>(lines.subList(index, index + methodLength));
        ArrayList<MethodParameter> methodParameters = methodDeclarationParsing.getParameters();

        methods.put(methodName, new Method(this, methodLines, methodParameters));

        return methodLength;
    }
}