package CodeParser;

import main.IllegalCodeException;

public class SyntaxException extends IllegalCodeException {
    public SyntaxException(String message) {
        super(message);

    }
}
