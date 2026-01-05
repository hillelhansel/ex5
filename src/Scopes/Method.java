package Scopes;

import CodeParser.Line;

import java.util.ArrayList;

public class Method extends Scope{
    public Method(Scope parent, ArrayList<Line> lines) {
        super(parent, lines);
    }
}
