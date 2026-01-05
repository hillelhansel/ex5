package Main;

public class SyntaxException extends IllegalCodeException {
    public SyntaxException(int lineNumber, String message) {
        super(lineNumber + " - " + message);

    }
}
