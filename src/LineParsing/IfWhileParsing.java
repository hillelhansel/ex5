package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfWhileParsing {
    private final ArrayList<String> blockParameters;

    public IfWhileParsing(Line line) {
        String content = line.getContent();
        this.blockParameters = extractBlockParameters(content);
    }

    public ArrayList<String> getBlockParameters() {
        return blockParameters;
    }

    private ArrayList<String> extractBlockParameters(String content) {
        ArrayList<String> params = new ArrayList<>();
        String body = extractContentInsideBrackets(content);

        Pattern p = Pattern.compile("\\s*" + RegexPatterns.ARGUMENT);
        Matcher m = p.matcher(body);
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
