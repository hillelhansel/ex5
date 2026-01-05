package Scopes;

import Main.LineClassification;
import Main.LineType;
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
        LineClassification lineClassification = new LineClassification();

        for(int i = 0; i < lines.size(); i++){
            LineType lineType = lineClassification.classifyLine(lines.get(i));

        }
    }
}
