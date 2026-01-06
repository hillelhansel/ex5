package Scopes;

import main.IllegalCodeException;

public class IllegalCodeInGlobalScope extends IllegalCodeException {
    public IllegalCodeInGlobalScope() {
        super("Illegal code in global scope");
    }
}
