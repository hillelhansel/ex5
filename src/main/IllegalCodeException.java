package main;

public class IllegalCodeException extends Exception {
    public IllegalCodeException(String message) {
        super("error in line " + message);
    }
}
