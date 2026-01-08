package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import Variables.VarTypes;
import Variables.VariableException;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodDeclarationParsing extends LineParsing {
    public final String methodName;
    public ArrayList<MethodParameter> parameters;

    public MethodDeclarationParsing(Line line) throws LineParsingException {
        super(line);
        this.methodName = extractNameBeforeBrackets(content);
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<MethodParameter> getParameters() {
        return parameters;
    }

    @Override
    public void parse() throws IllegalCodeException {
        String paramsContent = extractContentInsideBrackets(content);
        if (paramsContent.isEmpty()) {
            return;
        }

        Pattern p = Pattern.compile(RegexPatterns.FINAL + "(" + RegexPatterns.VAR_TYPE + ")" +
                        "\\s+" + "(" + RegexPatterns.VAR_NAME + ")");
        Matcher m = p.matcher(paramsContent);
        while (m.find()) {
            boolean isFinal = (m.group(1) != null);
            VarTypes type = VarTypes.fromString(m.group(2));
            String name = m.group(3);
            this.parameters.add(new MethodParameter(isFinal, type, name));
        }
    }
}