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
            switch (lineType){
                case EMPTY_LINE, COMMENT:
                    break;

                case VARIABLE_DECLARATION:
                    lines.get(i);
                    addVariabel(name, varFactory.getObject(name, isFinal, isInitialized, type, value));
                    break;

                case METHOD_DECLARATION:
                    String name = ;
                    ArrayList<String> methodeLines = new ArrayList<>();
                    int bracketCounter = 1;
                    while(bracketCounter > 0){
                        LineType innerLineType = lineClassification.classifyLine(lines.get(i));
                        i++;
                        if (innerLineType == LineType.IF_WHILE_BLOCK){
                            bracketCounter++;
                        }
                        else if(innerLineType == LineType.CLOSING_BRACKET){
                            bracketCounter--;
                        }
                        methodeLines.add(lines.get(i));
                    }
                    methods.add(name, new Method(name, methodeLines));
                    break;
                default:
                    throw new RuntimeException("Unexpected line type " + lineType);
            }
        }
    }
}
