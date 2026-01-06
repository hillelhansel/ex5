package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfWhileParsing extends LineParsing {
    private final ArrayList<String> blockParameters;

    public IfWhileParsing(Line line) {
        super(line);
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
}
