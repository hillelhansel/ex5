package syntax;

public class Line {
    private final String content;
    private final int lineIndex;
    private LineType lineType;

    public Line(String content, int lineIndex, LineType lineType) {
        this.content = content;
        this.lineIndex = lineIndex;
        this.lineType = lineType;
    }

    public void setLineType(LineType Type) {
        lineType = Type;
    }

    public String getContent() {
        return content;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public LineType getLineType() {
        return lineType;
    }
}
