package scope;

import main.IllegalCodeException;
import syntax.Line;

public interface LineHandler {
    int validate(Line line, Scope scope, int index) throws IllegalCodeException;
}
