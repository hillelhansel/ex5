package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class LineParsing {
    protected final String content;

    public LineParsing(Line line) throws IllegalCodeException {
        this.content = line.getContent();
        parse();
    }

    public abstract void parse() throws IllegalCodeException;

    protected String extractContentInsideBrackets(String text) {
        int start = text.indexOf('(');
        int end = text.lastIndexOf(')');
        if (start == -1 || end == -1 || end < start) return "";
        return text.substring(start + 1, end).trim();
    }

    protected ArrayList<String> splitByComma(String text) {
        if (text == null || text.isBlank()){
            return new ArrayList<>();
        }

        return Arrays.stream(text.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected String extractNameBeforeBrackets(String text) {
        int openBracketIndex = text.indexOf('(');
        if (openBracketIndex != -1) {
            String[] parts = text.substring(0, openBracketIndex).trim().split("\\s+");
            return parts[parts.length - 1];
        }
        return "";
    }

    protected Var parseVarPart(String var) {
        String[] parts = var.split("=");
        String name = parts[0].trim();

        String value = null;
        if (parts.length > 1) {
            value = parts[1].trim();
        }

        return new Var(name, value);
    }

    protected Matcher getHeaderMatcher(String text) throws LineParsingException {
        Pattern p = Pattern.compile("^\\s*" + RegexPatterns.FINAL + "(" + RegexPatterns.VAR_TYPE + ")");
        Matcher m = p.matcher(text);

        if (!m.find()) {
            throw new LineParsingException("Cannot identify variable type in: " + text);
        }
        return m;
    }
}