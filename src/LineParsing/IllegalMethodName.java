package LineParsing;

import main.IllegalCodeException;

public class IllegalMethodName extends IllegalCodeException {
    public IllegalMethodName() {
        super("Invalid method declaration");
    }
}
