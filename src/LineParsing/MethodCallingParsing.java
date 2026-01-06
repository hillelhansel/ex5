package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodCallingParsing {
    private final String methodName;
    private final ArrayList<String> methodParameters;

    public MethodCallingParsing(Line line) throws LineParsingException {
        String content = line.getContent();
        this.methodName = extractMethodName(content);
        this.methodParameters = extractMethodParameters(content);
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<String> getMethodParameters() {
        return methodParameters;
    }

    private String extractMethodName(String content) throws IllegalArgumentException, LineParsingException {
        Pattern p = Pattern.compile("\\s*" + RegexPatterns.METHOD_NAME + "\\s*\\(");
        Matcher m = p.matcher(content);

        if (m.find()) {
            return m.group(1);
        }
        throw new LineParsingException("illegal name");
    }

    private ArrayList<String> extractMethodParameters(String content){
        ArrayList<String> params = new ArrayList<>();

        String paramsContent = extractContentInsideBrackets(content);
        if (paramsContent.isEmpty()) {
            return params;
        }

        Pattern p = Pattern.compile("\\s*" + RegexPatterns.ARGUMENT);
        Matcher m = p.matcher(paramsContent);
        while (m.find()) {
            params.add(m.group(1));
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
