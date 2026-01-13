package scope.LineHandlers;

import main.IllegalCodeException;
import object.ObjectType;
import object.SObject;
import scope.LineHandler;
import scope.Scope;
import syntax.Line;

import java.util.ArrayList;

public class AssignmentHandler implements LineHandler {
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        ArrayList<Var> assignedVars = parse(line.getContent());

        for (Var var : assignedVars) {
            String varName = var.getName();
            String valueExpression = var.getValue();

            SObject targetVar = scope.resolveObject(varName);
            ObjectType incomingType = scope.resolveExpressionType(valueExpression);

            targetVar.tryAssign(incomingType);
        }
        return 1;
    }

    private ArrayList<Var> parse(String content) {
        ArrayList<Var> result = new ArrayList<>();

        String cleanContent = content.replace(";", "").trim();
        ArrayList<String> parts = lineParsing.splitByComma(cleanContent);

        for (String part : parts) {
            result.add(lineParsing.parseVarPart(part));
        }
        return result;
    }
}

