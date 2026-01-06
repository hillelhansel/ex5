package Validation;

public class ScopeValidationException extends RuntimeException {
    public ScopeValidationException(int lineNumber) {
        super(lineNumber + ": invalid scope");
    }
}
