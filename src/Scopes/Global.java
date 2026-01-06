package Scopes;

import CodeParser.Line;
import LineParsing.MethodDeclarationParsing;
import LineParsing.MethodParameter;
import LineParsing.VarDeclarationParsing;
import Variables.VariableException;
import main.IllegalCodeException; // Import חשוב

import java.util.ArrayList;
import java.util.HashMap;

public class Global extends Scope {
    private final HashMap<String, Method> methods = new HashMap<>();

    public Global(Scope parent, ArrayList<Line> lines) throws Exception {
        super(parent, lines);
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
                        VarDeclarationParsing parsedLine = new VarDeclarationParsing(line);
                        addVariables(parsedLine);
                        break;

                    case ASSIGNMENT, IF_WHILE_BLOCK, METHOD_CALL, RETURN:
                        throw new IllegalCodeInGlobalScope();

                    default:
                        break;
                }
            }
            catch (IllegalCodeException e) {
                throw new IllegalCodeException(line.getLineIndex() + ": " + e.getMessage());
            }
        }
    }

    private void secondPass() throws IllegalCodeException {
        ScopeValidation validation = new ScopeValidation();

        for (Method method : methods.values()) {
            try {
                validation.validate(method);
            }
            catch (IllegalCodeException e) {
                throw e;
            }
        }
    }

    private int addMethods(MethodDeclarationParsing methodDeclarationParsing, int index) throws IllegalCodeException {
        String methodName = methodDeclarationParsing.getMethodName();
        int methodLength = getBlockLength(lines, index);
        ArrayList<Line> methodLines = new ArrayList<>(lines.subList(index, index + methodLength));
        ArrayList<MethodParameter> methodParameters = methodDeclarationParsing.getParameters();
        methods.put(methodName, new Method(this, methodLines, methodParameters));
        return methodLength;
    }
}