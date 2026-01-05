package Scopes;

import java.util.ArrayList;

public class Block extends Scope{
    public Block(Scope parent, ArrayList<String> lines) {
        super(parent, lines);
    }
}
