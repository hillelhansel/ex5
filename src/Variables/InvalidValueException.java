package Variables;

public class InvalidValueException extends Exception {
    public InvalidValueException(String value, VarTypes type) {
        super("Invalid value for " + type.toString() + ": " + value);
    }

}
