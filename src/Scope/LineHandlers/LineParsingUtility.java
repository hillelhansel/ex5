package Scope.LineHandlers;

import CodeParser.RegexPatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LineParsingUtility {
    public String extractContentInsideBrackets(String text) {
        int start = text.indexOf('(');
        int end = text.lastIndexOf(')');
        if (start == -1 || end == -1 || end < start) return "";
        return text.substring(start + 1, end).trim();
    }

    public ArrayList<String> splitByComma(String text) {
        if (text == null || text.isBlank()){
            return new ArrayList<>();
        }

        return Arrays.stream(text.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public String extractNameBeforeBrackets(String text) {
        int openBracketIndex = text.indexOf('(');
        if (openBracketIndex != -1) {
            String[] parts = text.substring(0, openBracketIndex).trim().split("\\s+");
            return parts[parts.length - 1];
        }
        return "";
    }

    public Var parseVarPart(String var) {
        String[] parts = var.split("=");
        String name = parts[0].trim();

        String value = null;
        if (parts.length > 1) {
            value = parts[1].trim();
        }

        return new Var(name, value);
    }

    public Matcher getHeaderMatcher(String text) throws LineParsingException {
        Pattern p = Pattern.compile("^\\s*" + RegexPatterns.FINAL + "(" + RegexPatterns.VAR_TYPE + ")");
        Matcher m = p.matcher(text);

        if (!m.find()) {
            throw new LineParsingException("Cannot identify variable type in " + text);
        }
        return m;
    }
}

