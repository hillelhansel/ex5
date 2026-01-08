package main;

public class IllegalCodeException extends Exception {

    public IllegalCodeException(String message) {
        super(message);
    }

    public IllegalCodeException(int line, String message) {
        super("Error in line " + line + ": " + message);
    }
}