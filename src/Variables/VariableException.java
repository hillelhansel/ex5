package Variables;

import main.IllegalCodeException;

public class VariableException extends IllegalCodeException {
    public VariableException(String message) {
        super(message);
    }

    public VariableException(int line, String message) {
        super(line, message);
    }
}