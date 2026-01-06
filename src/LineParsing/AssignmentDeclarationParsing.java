package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignmentDeclarationParsing {
    private final ArrayList<Var> assignedVars;

    public AssignmentDeclarationParsing(Line line) {
        String content = line.getContent();
        this.assignedVars = extractAssignedVars(content);
    }

    public ArrayList<Var> getParams() {
        return assignedVars;
    }

    private ArrayList<Var> extractAssignedVars(String content){
        ArrayList<Var> params = new ArrayList<>();

        Pattern p = Pattern.compile("\\s*" + "(" + RegexPatterns.VAR_NAME + ")\\s*=\\s*" + "(" + RegexPatterns.ARGUMENT + ")");
        Matcher m = p.matcher(content);
        while (m.find()) {
            String name = m.group(1);
            String value = m.group(2);
            params.add(new Var(name, value));
        }
        return params;
    }
}
