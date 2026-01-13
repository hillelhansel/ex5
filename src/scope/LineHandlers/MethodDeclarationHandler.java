package scope.LineHandlers;

import main.IllegalCodeException;
import object.ObjectType;
import scope.*;
import syntax.Line;

import java.util.ArrayList;
import java.util.regex.Matcher;
public class MethodDeclarationHandler implements LineHandler {
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    private static class MethodDeclData {
        String name;
        ArrayList<MethodParameter> params;
    }
    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        MethodDeclData data = parse(line.getContent());

        if (scope.getScopeType() != ScopeType.GLOBAL) {
            throw new ScopeException("Global scope not found");
        }

        return ((Global) scope).addMethod(data.name, data.params, index);
    }

    private MethodDeclData parse(String content) throws IllegalCodeException {
        MethodDeclData data = new MethodDeclData();
        data.name = lineParsing.extractNameBeforeBrackets(content);
        data.params = new ArrayList<>();

        String paramsContent = lineParsing.extractContentInsideBrackets(content);
        for (String paramStr : lineParsing.splitByComma(paramsContent)) {
            Matcher m = lineParsing.getHeaderMatcher(paramStr);
            boolean isFinal = (m.group(1) != null);
            ObjectType type = ObjectType.fromString(m.group(2));
            String name = paramStr.substring(m.end()).trim();
            data.params.add(new MethodParameter(isFinal, type, name));
        }
        return data;
    }

}
