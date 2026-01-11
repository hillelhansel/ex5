package LineParsing;

import CodeParser.Line;
import main.IllegalCodeException;

import java.util.ArrayList;

public class AssignmentParsing extends LineParsing {
    private ArrayList<Var> assignedVars;

    public AssignmentParsing(Line line) throws IllegalCodeException {
        super(line);
    }

    public ArrayList<Var> getParams() {
        return assignedVars;
    }

    @Override
    public void parse() {
        this.assignedVars = new ArrayList<>();

        String cleanContent = content.replace(";", "").trim();

        ArrayList<String> parts = splitByComma(cleanContent);

        for (String part : parts) {
            assignedVars.add(parseVarPart(part));
        }
    }
}