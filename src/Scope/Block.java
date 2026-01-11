package Scope;

import CodeParser.Line;

import java.util.ArrayList;

public class Block extends Scope{
    public Block(Scope parent, ArrayList<Line> lines) {
        super(parent, lines,  ScopeType.BLOCK);
    }
}
