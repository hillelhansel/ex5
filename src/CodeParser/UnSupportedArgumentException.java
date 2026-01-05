package CodeParser;

import main.IllegalCodeException;

public class UnSupportedArgumentException extends IllegalCodeException {
    public UnSupportedArgumentException(int lineNumber) {
        super(lineNumber + ": unsupported argument");
    }
}
