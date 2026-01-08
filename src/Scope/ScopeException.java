package Scope;

import main.IllegalCodeException;

public class ScopeException extends IllegalCodeException {
    public ScopeException(String message) {
        super(message);
    }

    public ScopeException(int line, String message) {
        super(line, message);
    }
}