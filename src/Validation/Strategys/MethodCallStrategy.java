package Validation.Strategys;

import CodeParser.Line;
import LineParsing.MethodCallingParsing;
import LineParsing.MethodParameter;
import Scopes.Global;
import Scopes.Method;
import Scopes.Scope;
import Scopes.ScopeException;
import Variables.SObject;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;

public class MethodCallStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        MethodCallingParsing methodCallingParsing = new MethodCallingParsing(line);

        String methodName = methodCallingParsing.getMethodName();
        Global global = scope.getGlobalScope();

        Method methodToCall = global.getMethods().get(methodName);
        if (methodToCall == null) {
            throw new ScopeException(": Method " + methodName + " not found");
        }

        ArrayList<String> callParameters = methodCallingParsing.getMethodParameters();
        ArrayList<MethodParameter> methodParameters = methodToCall.getMethodParams();
        if(callParameters.size() != methodParameters.size()) {
            throw new ScopeException(": Wrong number of parameters");
        }

        for (int i = 0; i < callParameters.size(); i++) {
            String paramValue = callParameters.get(i);
            MethodParameter methodParameter = methodParameters.get(i);
            validateSingleCalling(paramValue, methodParameter, scope);
        }
        return 1;
    }

    private void validateSingleCalling(String paramValue, MethodParameter methodParameter, Scope scope) throws IllegalCodeException{
        VarTypes expectedType = methodParameter.getType();
        String paramName = methodParameter.getName();

        SObject object = scope.getObject(paramValue);
        if(object != null){
            if (!object.isInitialized()) {
                throw new ScopeException(": Variable '" + paramName + "' is not initialized");
            }
            if(object.getVarType() != expectedType){
                throw new ScopeException(": expected" + expectedType + " got" +  object.getVarType());
            }

        }

        else if(!isLiteralMatchingType(paramValue, expectedType)){
            throw new ScopeException(": value '" + paramValue + "' is not valid for type " + expectedType);
        }
    }
}
