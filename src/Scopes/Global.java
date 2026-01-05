package Scopes;

import Variables.VarFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class Global extends Scope{
    HashMap<String, Method> methods = new HashMap<String,Method>();
    private final VarFactory varFactory;


    public Global(Scope parent, ArrayList<String> lines) {
        super(parent, lines);
        this.varFactory = new VarFactory();

        firstPass(lines);
    }

    private void firstPass(ArrayList<String> lines){

        for(int i = 0; i < lines.size(); i++){

        }
    }
}
