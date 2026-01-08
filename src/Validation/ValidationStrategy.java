package Validation;

import CodeParser.Line;
import Scopes.Scope;
import main.IllegalCodeException;

public interface ValidationStrategy{
    int validate(Line line, Scope scope, int index) throws IllegalCodeException;
}
