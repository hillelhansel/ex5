package Scopes;

import CodeParser.Line;
import LineParsing.MethodDeclarationParsing;
import LineParsing.MethodParameter;
import LineParsing.VarDeclarationParsing;
import Validation.ScopeValidation;
import Variables.InvalidValueException;
import main.IllegalCodeException;

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

    private void firstPass(ArrayList<Line> lines) throws Exception {
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
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
                    throw new Exception("Error line " + line.getLineIndex() + ": Illegal code in global scope");

                default:
                    break;
            }
        }
    }

    private void secondPass() throws Exception {
        ScopeValidation validation = new ScopeValidation();
        methods.forEach((name, method) -> {
            try {
                validation.validate(method);
            }
            catch (InvalidValueException e) {
                throw new IllegalCodeException(e);
            }
        });
    }

    private int addMethods(MethodDeclarationParsing methodDeclarationParsing, int index) throws InvalidValueException {
        String methodName = methodDeclarationParsing.getMethodName();
        int methodLength = getBlockLength(lines, index);
        ArrayList<Line> methodLines = new ArrayList<>(lines.subList(index, index + methodLength));
        ArrayList<MethodParameter> methodParameters = methodDeclarationParsing.getParameters();
        methods.put(methodName, new Method(this, methodLines, methodParameters));
        return methodLength;
    }



}