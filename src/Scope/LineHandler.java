package Scope;

import CodeParser.Line;
import main.IllegalCodeException;

public interface LineHandler {
    int validate(Line line, Scope scope, int index) throws IllegalCodeException;
}
