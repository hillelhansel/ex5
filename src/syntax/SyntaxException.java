package syntax;

import main.IllegalCodeException;

public class SyntaxException extends IllegalCodeException {
    public SyntaxException(int lineIndex, String message) {
        super(lineIndex, message);
    }
}