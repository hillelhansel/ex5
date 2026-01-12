package scope.LineHandlers;

import syntax.Line;
import scope.Scope;
import scope.LineHandler;
import object.SObject;
import object.ObjectType;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class VarDeclarationHandler implements LineHandler {
    private boolean isFinal;
    private ObjectType type;
    private ArrayList<Var> variables;
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    public void parse(String content) throws IllegalCodeException {
        Matcher m = lineParsing.getHeaderMatcher(content);

        this.isFinal = (m.group(1) != null);
        this.type = ObjectType.fromString(m.group(2));
        String body = content.substring(m.end()).replace(";", "").trim();

        this.variables = new ArrayList<>();
        ArrayList<String> parts = lineParsing.splitByComma(body);

        for (String part : parts) {
            this.variables.add(lineParsing.parseVarPart(part));
        }
    }

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        parse(line.getContent());
        ObjectType declaredType = type;

        for (Var var : variables) {
            ObjectType valueType = null;

            if (var.getValue() != null) {
                valueType = scope.resolveExpressionType(var.getValue());
            }

            SObject sObject = new SObject(var.getName(), isFinal, declaredType, valueType);

            scope.addVariable(sObject, var.getName());
        }
        return 1;
    }
}