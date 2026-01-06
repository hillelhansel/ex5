package Scopes;

import CodeParser.Line;
import LineParsing.IfWhileParsing;
import LineParsing.Var;
import LineParsing.VarDeclarationParsing;
import Variables.InvalidValueException;
import Variables.SObject;
import Variables.VarFactory;
import Variables.VarTypes;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Scope {
    protected Scope parent;
    protected final ArrayList<Line> lines;
    protected final HashMap<String, SObject> localVariables;
    protected final VarFactory varFactory;


    public Scope(Scope parent, ArrayList<Line> lines) {
        this.parent = parent;
        this.lines = lines;
        this.varFactory = new VarFactory();
        this.localVariables = new HashMap<>();
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void addVariables(VarDeclarationParsing parsedLine) throws InvalidValueException {
        boolean isFinal = parsedLine.isFinal();
        VarTypes type = parsedLine.getType();
        for(Var var:parsedLine.getVariables()){
            String varName = var.getName();
            String varValue = var.getValue();
            SObject localVar = varFactory.getObject(varName, isFinal, type, varValue);
            addVariable(localVar, varName);
        }
    }

    protected void addVariable(SObject sObject, String varName){
        if(this.localVariables.containsKey(varName)){
            throw new DuplicateVariableException();
        }
        localVariables.put(varName, sObject);
    }

    protected int addIfWhile(IfWhileParsing ifWhileParsing, int index) {
        int methodLength = getBlockLength(lines, index);
        ArrayList<Line> methodLines = new ArrayList<>(lines.subList(index, index + methodLength));
        new Block(this, methodLines);
        return methodLength;
    }

    protected int getBlockLength(ArrayList<Line> lines, int startIndex) {
        int openBrackets = 0;
        int count = 0;
        for (int i = startIndex; i < lines.size(); i++) {
            String content = lines.get(i).getContent();
            if (content.contains("{")) openBrackets++;
            if (content.contains("}")) openBrackets--;
            count++;
            if (openBrackets == 0) return count;
        }
        return count;
    }

    protected boolean isObjectExists(String name) {
        if (localVariables.containsKey(name)) {
            return true;
        }
        else{
            parent.isObjectExists(name);
        }
        return false;
    }

    protected SObject getObject(String name) {
        return localVariables.get(name);
    }

    private VarTypes getVarType(String name) {
        if (isObjectExists(name)) {
            SObject object = getObject(name);
            if (object != null){
                return object.getVarType();
            }
            return parent.getVarType(name);
        }
        return null;
    }
}
