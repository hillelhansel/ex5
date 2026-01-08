package LineParsing;

import CodeParser.Line;

import java.util.ArrayList;

public class IfWhileParsing extends LineParsing {
    private ArrayList<String> parameters;

    public IfWhileParsing(Line line) {
        super(line);
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    @Override
    public void parse() {
        String insideBrackets = extractContentInsideBrackets(content);
        this.parameters = splitByComma(insideBrackets);
    }
}
