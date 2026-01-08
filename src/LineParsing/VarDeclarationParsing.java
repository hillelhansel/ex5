package LineParsing;

import CodeParser.Line;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class VarDeclarationParsing extends LineParsing {
    private boolean isFinal;
    private VarTypes type;
    private ArrayList<Var> variables;

    public VarDeclarationParsing(Line line) throws IllegalCodeException {
        super(line);
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

    @Override
    public void parse() throws IllegalCodeException {
        Matcher m = getHeaderMatcher(content);

        this.isFinal = (m.group(1) != null);
        this.type = VarTypes.fromString(m.group(2));
        String body = content.substring(m.end()).replace(";", "").trim();

        this.variables = new ArrayList<>();
        ArrayList<String> parts = splitByComma(body);

        for (String part : parts) {
            this.variables.add(parseVarPart(part));
        }
    }
}