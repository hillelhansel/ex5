package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignmentParsing extends LineParsing {
    private final ArrayList<Var> assignedVars;

    public AssignmentParsing(Line line) throws IllegalCodeException {
        super(line);
        this.assignedVars = new ArrayList<>();
    }

    public ArrayList<Var> getParams() {
        return assignedVars;
    }

    @Override
    public void parse(){
        String cleanContent = content.replace(";", "").trim();
        String regex = "\\s*" + RegexPatterns.VAR_NAME + "\\s*=\\s*" + RegexPatterns.ARGUMENT;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(cleanContent);
        while (m.find()) {
            assignedVars.add(new Var(m.group(1), m.group(2)));
        }
    }
}
