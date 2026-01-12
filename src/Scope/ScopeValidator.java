package Scope;

import CodeParser.Line;
import CodeParser.LineType;
import Scope.LineHandlers.*;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.HashMap;

public class ScopeValidator {
    private final HashMap<LineType, LineHandler> handlers = new HashMap<>();
    private int startingIndex = 1;

    public ScopeValidator() {
        handlers.put(LineType.ASSIGNMENT, new AssignmentHandler());
        handlers.put(LineType.VARIABLE_DECLARATION, new VarDeclarationHandler());
        handlers.put(LineType.IF_WHILE_BLOCK, new IfWhileHandler());
        handlers.put(LineType.METHOD_CALL, new MethodCallingHandler());
        handlers.put(LineType.METHOD_DECLARATION, new MethodDeclarationHandler());
    }

    public void validate(Scope scope) throws IllegalCodeException {
        ArrayList<Line> lines = scope.getLines();

        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);

            try{
                scope.validateLine(line);
            }
            catch(IllegalCodeException e){
                throw new IllegalCodeException(line.getLineIndex(), e.getMessage());
            }

            LineHandler handler = handlers.get(line.getLineType());
            if (handler == null) {
                continue;
            }

            try {
                int consumed = handler.validate(line, scope, i);
                i += consumed - 1;
            }
            catch (IllegalCodeException e) {
                throw new ScopeException(line.getLineIndex(), e.getMessage());
            }
        }

        scope.validateScopeEnd();
    }
}
