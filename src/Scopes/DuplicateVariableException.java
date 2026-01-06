package Scopes;

import main.IllegalCodeException;

public class DuplicateVariableException extends IllegalCodeException {
    public DuplicateVariableException(String message) {
        super(message);
    }
}
