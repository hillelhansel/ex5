package scope.LineHandlers;

import main.IllegalCodeException;
import object.ObjectType;
import scope.*;
import syntax.Line;

import java.util.ArrayList;

public class MethodCallingHandler implements LineHandler {
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    private static class MethodCallData {
        String name;
        ArrayList<String> args;
    }

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        MethodCallData call = parse(line.getContent());

        Method method = scope.getGlobalScope().getMethods().get(call.name);
        if (method == null) {
            throw new ScopeException("Method " + call.name + " not found");
        }

        if (call.args.size() != method.getMethodParams().size()) {
            throw new ScopeException("Wrong number of parameters");
        }

        for (int i = 0; i < call.args.size(); i++) {
            ObjectType argType = scope.resolveExpressionType(call.args.get(i));
            ObjectType expected = method.getMethodParams().get(i).getType();

            if (!argType.isTypeCompatible(argType, expected)) {
                throw new ScopeException("Wrong parameter type");
            }
        }
        return 1;
    }

    private MethodCallData parse(String content) {
        MethodCallData data = new MethodCallData();
        data.name = lineParsing.extractNameBeforeBrackets(content);
        data.args = lineParsing.splitByComma(
                lineParsing.extractContentInsideBrackets(content)
        );
        return data;
    }

}
