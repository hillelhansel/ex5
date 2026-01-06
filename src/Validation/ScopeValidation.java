package Validation;

import CodeParser.Line;
import LineParsing.*;
import Scopes.Global;
import Scopes.Method;
import Scopes.Scope;
import Variables.InvalidValueException;
import Variables.SObject;

import java.util.ArrayList;

public class ScopeValidation {
    public void validate(Scope scope) throws InvalidValueException {
        ArrayList<Line> scopeLines = scope.getLines();
        for (int i = 1; i < scopeLines.size(); i++) {
            Line line = scopeLines.get(i);
            switch (line.getLineType()) {
                case VARIABLE_DECLARATION:
                    VarDeclarationParsing parsedLine = new VarDeclarationParsing(line);
                    scope.addVariables(parsedLine);
                    break;
                case IF_WHILE_BLOCK:
                    IfWhileParsing ifWhileParsing = new IfWhileParsing(line);
                    int blockLength = addIfWhile(ifWhileParsing, i);
                    i += blockLength - 1;
                    break;
                case ASSIGNMENT:
                    AssignmentDeclarationParsing assignmentDeclarationParsing = new AssignmentDeclarationParsing(line);
                    ArrayList<Var> assignmentVariable = assignmentDeclarationParsing.getParams();
                    for (Var var : assignmentVariable) {
                        if (doesObjectExists(var.getName())) {
                            throw
                        }
                        SObject object = getObject(var.getName());
                        object.setValue(var.getValue());
                    }
                    break;
                case METHOD_CALL:
                    MethodCallingParsing methodCallingParsing = new MethodCallingParsing(line);
                    String methodName = methodCallingParsing.getMethodName();
                    Method methodToCall = Global.getMethods().get(methodName);
                    if (methodToCall == null) {
                        throw
                    }
                    ArrayList<String> parameters = methodCallingParsing.getMethodParameters();
                    for (int i = 0; i < parameters.size(); i++) {
                        MethodParameter methodParameter = ;
                        if(isValidInput(parameters.get(i))) {}

                    }
                    break;
            }
        }
    }
}
