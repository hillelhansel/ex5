package Scopes;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import LineParsing.*;
import Variables.VariableException;
import Variables.SObject;
import Variables.VarTypes;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ScopeValidation {
    public void validate(Scope scope) throws VariableException, ScopeException, LineParsingException {
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
                    int blockLength = scope.addIfWhile(ifWhileParsing, i);
                    i += blockLength - 1;
                    break;
                case ASSIGNMENT:
                    AssignmentDeclarationParsing assignmentDeclarationParsing = new AssignmentDeclarationParsing(line);
                    ArrayList<Var> assignmentVariable = assignmentDeclarationParsing.getParams();
                    for (Var var : assignmentVariable) {
                        if (scope.doesObjectExists(var.getName())) {
                            throw
                        }
                        SObject object = scope.getObject(var.getName());
                        object.setValue(var.getValue());
                    }
                    break;
                case METHOD_CALL:
                    MethodCallingParsing methodCallingParsing = new MethodCallingParsing(line);
                    String methodName = methodCallingParsing.getMethodName();
                    Global global = scope.getGlobalScope();
                    Method methodToCall = global.getMethods().get(methodName);
                    if (methodToCall == null) {
                        throw MethodDoNotExist
                    }

                    ArrayList<String> callParameters = methodCallingParsing.getMethodParameters();
                    ArrayList<MethodParameter> methodParameters = methodToCall.getMethodParams();
                    if(callParameters.size() != methodParameters.size()) {
                        throw NotEnoughParam
                    }

                    for (int i = 0; i < callParameters.size(); i++) {
                        String paramValue = callParameters.get(i);
                        MethodParameter methodParameter = methodParameters.get(i);
                        validateSingleCalling(paramValue, methodParameter, scope);
                    }
                    break;
            }
        }
    }

    private void addDeclaredVariabels(Line line) {

    }

    private void validateSingleCalling(String paramValue, MethodParameter methodParameter, Scope scope) throws Exception{
        VarTypes expectedType = methodParameter.getType();

        SObject object = scope.getObject(paramValue);
        if(object != null){
            if(!object.isInitialized()){
                throw invalid
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
        String regex = "";
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
