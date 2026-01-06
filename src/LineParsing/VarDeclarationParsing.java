package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import Variables.VarTypes;
import Variables.VariableException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VarDeclarationParsing extends LineParsing {
    private final boolean isFinal;
    private final VarTypes type;
    private final ArrayList<Var> variables;

    public VarDeclarationParsing(Line line) {
        super(line);
        this.isFinal = parseIsFinal(content);
        this.type = extractVarType(content);
        this.variables = extractVariables(content);
    }

    public boolean isFinal() {
        return isFinal;
    }

    public VarTypes getType() {
        return type;
    }

    public ArrayList<Var> getVariables() {
        return variables;
    }

    private boolean parseIsFinal(String content) {
        return content.trim().startsWith("final");
    }

    private VarTypes extractVarType(String content) throws VariableException, LineParsingException {
        Matcher m = Pattern.compile(RegexPatterns.FINAL + "(" + RegexPatterns.VAR_TYPE + ")").matcher(content);
        if (m.find()) {
            return VarTypes.fromString(m.group(2));
        }
        throw new LineParsingException("Cannot identify variable type in: " + content);
    }

    private ArrayList<Var> extractVariables(String content) {
        ArrayList<Var> vars = new ArrayList<>();

        Matcher headerMatcher = Pattern.compile("^\\s*" + RegexPatterns.FINAL + RegexPatterns.VAR_TYPE).matcher(content);
        if (!headerMatcher.find()) return vars;

        String body = content.substring(headerMatcher.end());
        Pattern p = Pattern.compile(RegexPatterns.VAR_NAME + "(?:\\s*=\\s*([^,;]+))?");
        Matcher m = p.matcher(body);

        while (m.find()) {
            String name = m.group(1);
            String val = m.group(2);
            if (val != null) {
                val = val.trim();
            }

            vars.add(new Var(name, val));
        }
        return vars;
    }
}