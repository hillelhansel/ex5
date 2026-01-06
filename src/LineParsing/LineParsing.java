package LineParsing;

import CodeParser.Line;

public abstract class LineParsing {
    protected final String content;
    public LineParsing(Line line) {
        this.content = line.getContent();
    }

    protected String extractContentInsideBrackets(String content) {
        int start = content.indexOf('(');
        int end = content.lastIndexOf(')');

        if (start != -1 && end != -1 && end > start) {
            return content.substring(start + 1, end).trim();
        }
        return "";
    }
}
