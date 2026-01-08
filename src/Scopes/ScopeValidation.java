package Scopes;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import LineParsing.*;
import Variables.SObject;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ScopeValidation {
    public void validate(Scope scope) throws IllegalCodeException {
        ArrayList<Line> scopeLines = scope.getLines();

        for (int i = 1; i < scopeLines.size(); i++) {
            Line line = scopeLines.get(i);
            switch (line.getLineType()) {
                case VARIABLE_DECLARATION:
                    handleVarDeclaration(line,  scope);
                    break;
                case IF_WHILE_BLOCK:
                    int blockLength = handleIfWhileDeclaration(line, scope, i);
                    i += blockLength - 1;
                    break;
                case ASSIGNMENT:
                    handelAssignment(line, scope);
                    break;
                case METHOD_CALL:
                    handleMethodCall(line, scope);
                    break;
            }
        }
    }

    private void handleVarDeclaration(Line line, Scope scope) throws IllegalCodeException {
        VarDeclarationParsing parser = new VarDeclarationParsing(line);
        scope.addVariables(parser);
    }

    private int handleIfWhileDeclaration(Line line, Scope scope, int index) {
        IfWhileParsing ifWhileParsing = new IfWhileParsing(line);
        return scope.addIfWhile(ifWhileParsing, index);
    }

    private void handelAssignment(Line line, Scope scope) throws IllegalCodeException {
        AssignmentDeclarationParsing parser = new AssignmentDeclarationParsing(line);
        ArrayList<Var> assignmentVariable = parser.getParams();

        for (Var var : assignmentVariable) {
            String varName = var.getName();
            String valueExpression = var.getValue();
            SObject targetVar = scope.getObject(varName);

            if (targetVar == null) {
                throw new ScopeException(line.getLineIndex() + ": Variable '" + varName + "' is not defined");
            }

            if (targetVar.isFinal()) {
                throw new ScopeException(line.getLineIndex() + ": Cannot modify final variable '" + varName + "'");
            }

            SObject sourceVar = scope.getObject(valueExpression);
            if (sourceVar != null) {
                if (!sourceVar.isInitialized()) {
                    throw new ScopeException(line.getLineIndex() + ": Variable '" + valueExpression + "' is not initialized");
                }

                if (sourceVar.getVarType() != targetVar.getVarType()) {
                    throw new ScopeException(line.getLineIndex() + ": cannot assign " + sourceVar.getVarType() + " to " + targetVar.getVarType());
                }
            }
            else {
                if (!isLiteralMatchingType(valueExpression, targetVar.getVarType())) {
                    throw new ScopeException(line.getLineIndex() + ": value '" + valueExpression + "' is not valid for type " + targetVar.getVarType());
                }
            }
            targetVar.setIsInitialized(true);
        }
    }

    private void handleMethodCall(Line line, Scope scope) throws IllegalCodeException {
        MethodCallingParsing methodCallingParsing = new MethodCallingParsing(line);
        String methodName = methodCallingParsing.getMethodName();
        Global global = scope.getGlobalScope();
        Method methodToCall = global.getMethods().get(methodName);
        if (methodToCall == null) {
            throw new ScopeException(line.getLineIndex() + ": Method " + methodName + " not found");
        }

        ArrayList<String> callParameters = methodCallingParsing.getMethodParameters();
        ArrayList<MethodParameter> methodParameters = methodToCall.getMethodParams();
        if(callParameters.size() != methodParameters.size()) {
            throw new ScopeException(line.getLineIndex() + ": Wrong number of parameters");
        }

        for (int i = 0; i < callParameters.size(); i++) {
            String paramValue = callParameters.get(i);
            MethodParameter methodParameter = methodParameters.get(i);
            validateSingleCalling(paramValue, methodParameter, scope);
        }
    }

    private void validateSingleCalling(String paramValue, MethodParameter methodParameter, Scope scope) throws IllegalCodeException{
        VarTypes expectedType = methodParameter.getType();

        SObject object = scope.getObject(paramValue);
        if(object != null){
            if(!object.isInitialized()){
                throw new ScopeException()
            }
            if(object.getVarType() != expectedType){
                throw invalid
            }

        }

        else if(!isLiteralMatchingType(paramValue, expectedType)){
            throw invalid
        }
    }

    private boolean isLiteralMatchingType(String value, VarTypes expectedType) {
        String regex;
        switch (expectedType) {
            case SINT: regex = RegexPatterns.INT; break;
            case SDOUBLE: regex = RegexPatterns.DOUBLE; break;
            case SSTRING: regex = RegexPatterns.STRING; break;
            case SCHAR: regex = RegexPatterns.CHAR; break;
            case SBOOLEAN: regex = RegexPatterns.BOOLEAN; break;
            default: return false;
        }
        return Pattern.matches(regex, value);
    }
}
