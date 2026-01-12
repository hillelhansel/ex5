package Scope.Validation;

import CodeParser.Line;
import Scope.Scope;
import main.IllegalCodeException;

public interface ValidationStrategy{
    int validate(Line line, Scope scope, int index) throws IllegalCodeException;
    void parse(String content);
}
