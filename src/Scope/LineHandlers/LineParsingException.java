package Scope.LineHandlers;

import main.IllegalCodeException;

public class LineParsingException extends IllegalCodeException {
    public LineParsingException(String message) {
        super(message);
    }

    public LineParsingException(int line, String message) {
        super(line, message);
    }
}