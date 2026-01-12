package scope.LineHandlers;

import syntax.Line;
import scope.Scope;
import scope.LineHandler;
import object.ObjectType;
import main.IllegalCodeException;
import scope.ScopeType;
import scope.Global;
import scope.ScopeException;

import java.util.ArrayList;
import java.util.regex.Matcher;
public class MethodDeclarationHandler implements LineHandler {
    private String methodName;
    private ArrayList<MethodParameter> parameters;
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    public void parse(String content) throws IllegalCodeException {
        this.methodName = lineParsing.extractNameBeforeBrackets(content);
        String paramsContent = lineParsing.extractContentInsideBrackets(content);

        this.parameters = new ArrayList<>();
        ArrayList<String> paramStrings = lineParsing.splitByComma(paramsContent);

        for (String paramStr : paramStrings) {
            Matcher m = lineParsing.getHeaderMatcher(paramStr);

            boolean IsFinal = (m.group(1) != null);
            ObjectType Type = ObjectType.fromString(m.group(2));

            String Name = paramStr.substring(m.end()).trim();

            this.parameters.add(new MethodParameter(IsFinal, Type, Name));
        }
    }

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        parse(line.getContent());
        if (scope.getScopeType() != ScopeType.GLOBAL) {
            throw new ScopeException("Global scope not found");
        }
        Global global = (Global) scope;
        return global.addMethod(methodName, parameters, index);
    }
}
