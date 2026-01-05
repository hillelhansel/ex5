package Validation;

import main.IllegalCodeException;

public class SyntaxException extends IllegalCodeException {
    public SyntaxException(int lineNumber) {
        super(lineNumber + ": invalid syntax");

    }
}
