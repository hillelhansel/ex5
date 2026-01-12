package LineParsing;

import CodeParser.Line;
import Scope.Scope;
import Scope.Validation.ValidationStrategy;
import Variables.VarTypes;
import main.IllegalCodeException;
import Scope.Global;
import Scope.Method;
import Scope.ScopeException;

import java.util.ArrayList;

public class MethodCallingParsing {
    private String methodName;
    private ArrayList<String> parameters;
    private LineParsingUtility lineParsing = new LineParsingUtility();


    public ValidationStrategy getValidationStrategy() {
        return new MethodCallStrategy();
    }

    public void parse(String content) {
        this.methodName = lineParsing.extractNameBeforeBrackets(content);
        String insideBrackets = lineParsing.extractContentInsideBrackets(content);
        this.parameters = lineParsing.splitByComma(insideBrackets);
    }

    public class MethodCallStrategy implements ValidationStrategy {
        @Override
        public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
            parse(line.getContent());
            Global global = scope.getGlobalScope();

            Method methodToCall = global.getMethods().get(methodName);
            if (methodToCall == null) {
                throw new ScopeException(": Method " + methodName + " not found");
            }

            ArrayList<String> callArgs = parameters;
            ArrayList<MethodParameter> methodParams = methodToCall.getMethodParams();

            if (callArgs.size() != methodParams.size()) {
                throw new ScopeException(": Wrong number of parameters");
            }

            for (int i = 0; i < callArgs.size(); i++) {
                String argValue = callArgs.get(i);
                MethodParameter paramDef = methodParams.get(i);

                VarTypes type = scope.resolveExpressionType(argValue);
                if (!type.isTypeCompatible(type, paramDef.getType())){
                    throw new ScopeException(": Wrong parameter type");
                }
            }
            return 1;
        }
    }
}
