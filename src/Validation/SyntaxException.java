package Validation;

import Main.IllegalCodeException;

public class SyntaxException extends IllegalCodeException {
    public SyntaxException(int lineNumber, String message) {
        //todo
        super(lineNumber + " - " + message);

    }
}
