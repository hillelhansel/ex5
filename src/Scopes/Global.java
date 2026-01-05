package Scopes;

import CodeParser.Line;
import Variables.VarFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class Global extends Scope{
    HashMap<String, Method> methods = new HashMap<String,Method>();
    private final VarFactory varFactory;


    public Global(Scope parent, ArrayList<Line> lines) {
        super(parent, lines);
        this.varFactory = new VarFactory();

        firstPass(lines);
    }

    private void firstPass(ArrayList<Line> lines){
        for(int i = 0; i < lines.size(); i++){
            switch (lines.get(i).getLineType()){
                case METHOD_CALL ->
                case VARIABLE_DECLARATION ->
                default -> throw new IllegalStateException("Unexpected value: " + lines.get(i).getLineType());
            }
        }
    }
}
