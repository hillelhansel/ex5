package LineParsing;

import CodeParser.Line;
import main.IllegalCodeException;

import java.util.ArrayList;

public class MethodCallingParsing extends LineParsing {
    private String methodName;
    private ArrayList<String> parameters;

    public MethodCallingParsing(Line line) throws IllegalCodeException {
        super(line);
        parse();
    }

    @Override
    public void parse() {
        this.methodName = extractNameBeforeBrackets(content);
        String insideBrackets = extractContentInsideBrackets(content);
        this.parameters = splitByComma(insideBrackets);
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }
}
