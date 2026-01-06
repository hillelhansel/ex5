package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import Variables.VarTypes;
import Variables.VariableException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodDeclarationParsing extends LineParsing {
    public final String methodName;
    public final ArrayList<MethodParameter> parameters;

    public MethodDeclarationParsing(Line line) throws LineParsingException {
        super(line);
        this.methodName = extractMethodName(content);
        this.parameters = extractParameters(content);
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<MethodParameter> getParameters() {
        return parameters;
    }

    private String extractMethodName(String content) throws LineParsingException {
        Pattern p = Pattern.compile("void\\s+" + RegexPatterns.METHOD_NAME + "\\s*\\(");
        Matcher m = p.matcher(content);

        if (m.find()) {
            return m.group(1);
        }
        throw new LineParsingException("illegal name");
    }

    private ArrayList<MethodParameter> extractParameters(String content) throws VariableException {
        ArrayList<MethodParameter> params = new ArrayList<>();

        String paramsContent = extractContentInsideBrackets(content);
        if (paramsContent.isEmpty()) {
            return params;
        }

        Pattern p = Pattern.compile(RegexPatterns.FINAL + "(" + RegexPatterns.VAR_TYPE + ")" +
                        "\\s+" + "(" + RegexPatterns.VAR_NAME + ")");
        Matcher m = p.matcher(paramsContent);
        while (m.find()) {
            boolean isFinal = (m.group(1) != null);
            VarTypes type = VarTypes.fromString(m.group(2));
            String name = m.group(3);
            params.add(new MethodParameter(isFinal, type, name));
        }
        return params;
    }
}