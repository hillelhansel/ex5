package scope.LineHandlers;

import main.IllegalCodeException;
import object.ObjectType;
import object.SObject;
import scope.LineHandler;
import scope.Scope;
import syntax.Line;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class VarDeclarationHandler implements LineHandler {
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    private static class DeclarationData {
        boolean isFinal;
        ObjectType type;
        ArrayList<Var> variables;
    }

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        DeclarationData data = parse(line.getContent());

        for (Var var : data.variables) {
            ObjectType valueType = null;

            if (var.getValue() != null) {
                valueType = scope.resolveExpressionType(var.getValue());
            }

            SObject sObject = new SObject(var.getName(), data.isFinal, data.type, valueType);

            scope.addVariable(sObject, var.getName());
        }
        return 1;
    }

    private DeclarationData parse(String content) throws IllegalCodeException {
        Matcher m = lineParsing.getHeaderMatcher(content);

        DeclarationData data = new DeclarationData();
        data.isFinal = (m.group(1) != null);
        data.type = ObjectType.fromString(m.group(2));

        String body = content.substring(m.end()).replace(";", "").trim();
        data.variables = new ArrayList<>();

        for (String part : lineParsing.splitByComma(body)) {
            data.variables.add(lineParsing.parseVarPart(part));
        }
        return data;
    }
}