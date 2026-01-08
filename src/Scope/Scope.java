package Scope;

import CodeParser.Line;
import LineParsing.Var;
import LineParsing.VarDeclarationParsing;
import Variables.VariableException;
import Variables.SObject;
import Variables.VarTypes;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Scope {
    protected Scope parent;
    protected final ArrayList<Line> lines;
    protected final HashMap<String, SObject> localVariables;

    public Scope(Scope parent, ArrayList<Line> lines) {
        this.parent = parent;
        this.lines = lines;
        this.localVariables = new HashMap<>();
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void addVariables(VarDeclarationParsing parsedLine) throws VariableException, ScopeException {
        boolean isFinal = parsedLine.isFinal();
        VarTypes type = parsedLine.getType();

        for(Var var:parsedLine.getVariables()){
            String varName = var.getName();
            SObject localVar = new SObject(varName, isFinal, type, var.getValue());
            addVariable(localVar, varName);
        }
    }

    public SObject getObject(String name) {
        if (localVariables.containsKey(name)) {
            return localVariables.get(name);
        }
        if (parent != null) {
            return parent.getObject(name);
        }
        return null;
    }

    public Global getGlobalScope() throws ScopeException {
        Scope current = this;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        //todo
        if (current instanceof Global) {
            return (Global) current;
        }
        throw new ScopeException("Global scope not found structure error");
    }

    public Scope getParent() {
        return parent;
    }

    protected void addVariable(SObject sObject, String varName) throws ScopeException {
        if(this.localVariables.containsKey(varName)){
            throw new ScopeException("variable already exist");
        }
        localVariables.put(varName, sObject);
    }

    public Block addIfWhile(int index) throws ScopeException {
        int blockLength = scopeLength(lines, index);
        ArrayList<Line> methodLines = new ArrayList<>(lines.subList(index, index + blockLength));
        return new Block(this, methodLines);
    }

    public void validateScopeEnd() throws ScopeException { }

    protected int scopeLength(ArrayList<Line> lines, int startIndex) throws ScopeException {
        int openBrackets = 0;
        int count = 0;
        for (int i = startIndex; i < lines.size(); i++) {
            String content = lines.get(i).getContent();
            if (content.contains("{")){
                openBrackets++;
            }
            if (content.contains("}")){
                openBrackets--;
            }
            count++;
            if (openBrackets == 0){
                return count;
            }
        }
        if(openBrackets != 0){
            throw new ScopeException(": Missing closing bracket");
        }
        return count;
    }
}
