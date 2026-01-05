package Validation;

public class ScopeException extends RuntimeException {
    public ScopeException(int lineNumber) {
        super(lineNumber + ": invalid scope");
    }
}
