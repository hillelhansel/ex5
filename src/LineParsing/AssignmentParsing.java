package LineParsing;

import CodeParser.Line;
import Scope.Scope;
import Scope.Validation.ValidationStrategy;
import Variables.SObject;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;

public class AssignmentParsing {
    private ArrayList<Var> assignedVars;
    private LineParsingUtility lineParsing;

    public ValidationStrategy getValidationStrategy() {
        return new AssignmentStrategy();
    }

    public void parse(String content) {
        this.assignedVars = new ArrayList<>();
        String cleanContent = content.replace(";", "").trim();
        ArrayList<String> parts = lineParsing.splitByComma(cleanContent);

        for (String part : parts) {
            assignedVars.add(lineParsing.parseVarPart(part));
        }
    }

    public class AssignmentStrategy implements ValidationStrategy {
        @Override
        public int validate(Line line, Scope scope, int index) throws IllegalCodeException {
            parse(line.getContent());
            ArrayList<Var> assignmentVariable = assignedVars;

            for (Var var : assignmentVariable) {
                String varName = var.getName();
                String valueExpression = var.getValue();

                SObject targetVar = scope.resolveObject(varName);

                VarTypes incomingType = scope.resolveExpressionType(valueExpression);
                targetVar.tryAssign(incomingType);
            }
            return 1;
        }
    }
}
