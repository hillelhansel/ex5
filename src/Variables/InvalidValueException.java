package Variables;

import main.IllegalCodeException;

public class InvalidValueException extends IllegalCodeException {
    public InvalidValueException(String value, VarTypes type) {
        super("Invalid value for " + type.toString() + ": " + value);
    }

}
