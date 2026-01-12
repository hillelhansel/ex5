package scope;

import syntax.Line;
import syntax.LineType;
import main.IllegalCodeException;

import java.util.ArrayList;

public class Block extends Scope{
    public Block(Scope parent, ArrayList<Line> lines) {
        super(parent, lines,  ScopeType.BLOCK);
    }

    @Override
    public void validateLine(Line line) throws IllegalCodeException {
        if (line.getLineType() == LineType.METHOD_DECLARATION) {
            throw new ScopeException("Nested methods not allowed");
        }
    }
}
