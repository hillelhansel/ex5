package scope.LineHandlers;

import main.IllegalCodeException;
import object.ObjectType;
import object.SObject;
import scope.LineHandler;
import scope.Scope;
import syntax.Line;

import java.util.ArrayList;

public class AssignmentHandler implements LineHandler {
    private ArrayList<Var> assignedVars;
    private final LineParsingUtility lineParsing = new LineParsingUtility();

    @Override
    public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
        parse(line.getContent());
        ArrayList<Var> assignmentVariable = assignedVars;

        for (Var var : assignmentVariable) {
            String varName = var.getName();
            String valueExpression = var.getValue();

            SObject targetVar = scope.resolveObject(varName);

            ObjectType incomingType = scope.resolveExpressionType(valueExpression);
            targetVar.tryAssign(incomingType);
        }
        return 1;
    }

    private void parse(String content) {
        this.assignedVars = new ArrayList<>();
        String cleanContent = content.replace(";", "").trim();
        ArrayList<String> parts = lineParsing.splitByComma(cleanContent);

        for (String part : parts) {
            assignedVars.add(lineParsing.parseVarPart(part));
        }
    }
}

