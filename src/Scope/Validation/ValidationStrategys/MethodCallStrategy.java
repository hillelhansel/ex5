package Scope.Validation.ValidationStrategys;

import CodeParser.Line;
import LineParsing.MethodCallingParsing;
import LineParsing.MethodParameter;
import Scope.Global;
import Scope.Method;
import Scope.Scope;
import Scope.ScopeException;
import main.IllegalCodeException;

import java.util.ArrayList;

public class MethodCallStrategy extends BaseStrategy {
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        MethodCallingParsing parser = new MethodCallingParsing(line);
        Global global = scope.getGlobalScope();

        Method methodToCall = global.getMethods().get(parser.getMethodName());
        if (methodToCall == null) {
            throw new ScopeException(": Method " + parser.getMethodName() + " not found");
        }

        ArrayList<String> callArgs = parser.getParameters();
        ArrayList<MethodParameter> methodParams = methodToCall.getMethodParams();

        if (callArgs.size() != methodParams.size()) {
            throw new ScopeException(": Wrong number of parameters");
        }

        for (int i = 0; i < callArgs.size(); i++) {
            String argValue = callArgs.get(i);
            MethodParameter paramDef = methodParams.get(i);

            validateValueByType(scope, argValue, paramDef.getType());
        }
        return 1;
    }
}