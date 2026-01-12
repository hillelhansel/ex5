package main;

public class IllegalCodeException extends Exception {

    public IllegalCodeException(String message) {
        super(message);
    }

    public IllegalCodeException(int lineIndex, String message) {
        super("Error in line " + lineIndex + ": " + message);
    }
}