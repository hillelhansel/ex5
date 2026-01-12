package Scope.LineHandlers;

import CodeParser.Line;
import Scope.Scope;
import Scope.LineHandler;
import Variables.SObject;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class VarDeclarationHandler implements LineHandler {
    private boolean isFinal;
    private VarTypes type;
    private ArrayList<Var> variables;
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    public void parse(String content) throws IllegalCodeException {
        Matcher m = lineParsing.getHeaderMatcher(content);

        this.isFinal = (m.group(1) != null);
        this.type = VarTypes.fromString(m.group(2));
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
        VarTypes declaredType = type;

        for (Var var : variables) {
            VarTypes valueType = null;

            if (var.getValue() != null) {
                valueType = scope.resolveExpressionType(var.getValue());
            }

            SObject sObject = new SObject(var.getName(), isFinal, declaredType, valueType);

            scope.addVariable(sObject, var.getName());
        }
        return 1;
    }
}