package scope.LineHandlers;

import main.IllegalCodeException;

public class LineParsingException extends IllegalCodeException {
    public LineParsingException(String message) {
        super(message);
    }
}