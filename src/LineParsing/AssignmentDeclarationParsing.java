package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignmentDeclarationParsing extends LineParsing {
    private ArrayList<Var> assignedVars;

    public AssignmentDeclarationParsing(Line line) throws IllegalCodeException {
        super(line);
        this.assignedVars = new ArrayList<>();
    }

    public ArrayList<Var> getParams() {
        return assignedVars;
    }

    @Override
    public void parse(){
        Pattern p = Pattern.compile("\\s*" + RegexPatterns.VAR_NAME + "\\s*=\\s*" + RegexPatterns.ARGUMENT);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String name = m.group(1);
            String value = m.group(2);
            assignedVars.add(new Var(name, value));
        }
    }
}
