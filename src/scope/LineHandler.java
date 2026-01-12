package scope;

import syntax.Line;
import main.IllegalCodeException;

public interface LineHandler {
    int validate(Line line, Scope scope, int index) throws IllegalCodeException;
}
