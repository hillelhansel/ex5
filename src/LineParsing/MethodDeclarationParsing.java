package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import Variables.VarTypes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodDeclarationParsing {
    public final String methodName;
    public final ArrayList<MethodParameter> parameters;

    public MethodDeclarationParsing(Line line) {
        String content = line.getContent();
        this.methodName = extractMethodName(content);
        this.parameters = extractParameters(content);
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<MethodParameter> getParameters() {
        return parameters;
    }

    private String extractMethodName(String content) throws IllegalArgumentException{
        Pattern p = Pattern.compile("void\\s+(" + RegexPatterns.METHOD_NAME + ")\\s*\\(");
        Matcher m = p.matcher(content);

        if (m.find()) {
            return m.group(1);
        }
        throw new IllegalArgumentException("Invalid method declaration: " + content);
    }

    private ArrayList<MethodParameter> extractParameters(String content) {
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
            VarTypes type = VarTypes.convertStringToEnum(m.group(2));
            String name = m.group(3);
            params.add(new MethodParameter(isFinal, type, name));
        }
        return params;
    }

    private String extractContentInsideBrackets(String content) {
        int start = content.indexOf('(');
        int end = content.lastIndexOf(')');

        if (start != -1 && end != -1 && end > start) {
            return content.substring(start + 1, end).trim();
        }
        return "";
    }
}