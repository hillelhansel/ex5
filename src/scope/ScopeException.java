package scope;

import main.IllegalCodeException;

public class ScopeException extends IllegalCodeException {
    public ScopeException(String message) {
        super(message);
    }

    public ScopeException(int lineIndex, String message) {
        super(lineIndex, message);
    }
}