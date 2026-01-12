package object;

import main.IllegalCodeException;

public class ObjectException extends IllegalCodeException {
    public ObjectException(String message) {
        super(message);
    }
}