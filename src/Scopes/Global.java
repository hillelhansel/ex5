package Scopes;

import CodeParser.Line;
import LineParsing.MethodDeclarationParsing;
import LineParsing.Var;
import LineParsing.VarDeclarationParsing;
import Variables.SObject;
import Variables.VarFactory;
import Variables.VarTypes;

import java.util.ArrayList;
import java.util.HashMap;

public class Global extends Scope {
    HashMap<String, Method> methods = new HashMap<>();
    private final VarFactory varFactory;

    public Global(Scope parent, ArrayList<Line> lines) throws Exception {
        super(parent, lines);
        this.varFactory = new VarFactory();
        firstPass(lines);
    }

    private void firstPass(ArrayList<Line> lines) throws Exception {
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            switch (line.getLineType()) {
                case METHOD_DECLARATION:
                    MethodDeclarationParsing methodDeclarationParsing = new MethodDeclarationParsing(line);
                    String methodName = methodDeclarationParsing.getMethodName();
                    int methodLength = getBlockLength(lines, i);
                    ArrayList<Line> methodLines = new ArrayList<>(lines.subList(i, i + methodLength));
                    methods.put(methodName, new Method(this, methodLines));
                    i += methodLength - 1;
                    break;

                case VARIABLE_DECLARATION:
                    VarDeclarationParsing parsedLine = new VarDeclarationParsing(line);
                    boolean isFinal = parsedLine.isFinal();
                    VarTypes type = parsedLine.getType();
                    for(Var var:  parsedLine.getVariables()) {
                        String varName = var.getName();
                        String varValue = var.getValue();
                        boolean isInitialized = (varValue != null);
                        SObject localVar = varFactory.getObject(varName, isFinal, isInitialized, type, varValue);
                        addVariabele(localVar, varName);
                    }
                    break;

                case ASSIGNMENT, IF_WHILE_BLOCK, METHOD_CALL, RETURN:
                    throw new Exception("Error line " + line.getLineIndex() + ": Illegal code in global scope");

                default:
                    break;
            }
        }
    }

    private int getBlockLength(ArrayList<Line> lines, int startIndex) {
        int openBrackets = 0;
        int count = 0;
        for (int i = startIndex; i < lines.size(); i++) {
            String content = lines.get(i).getContent();
            if (content.contains("{")) openBrackets++;
            if (content.contains("}")) openBrackets--;
            count++;
            if (openBrackets == 0) return count;
        }
        return count;
    }

}