package Scope;

import CodeParser.Line;
import Variables.SObject;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Scope {
    protected Scope parent;
    protected final ArrayList<Line> lines;
    protected final HashMap<String, SObject> localVariables;
    private final ScopeType scopeType;

    public Scope(Scope parent, ArrayList<Line> lines, ScopeType scopeType) {
        this.parent = parent;
        this.lines = lines;
        this.localVariables = new HashMap<>();
        this.scopeType = scopeType;
    }

    public ArrayList<Line> getLines() {
        return lines;
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

    public ScopeType getScopeType() {
        return scopeType;
    }

    public Global getGlobalScope() throws ScopeException {
        Scope current = this;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        if (current.scopeType == ScopeType.GLOBAL) {
            return (Global) current;
        }
        throw new ScopeException("Global scope not found");
    }

    protected Scope getParent() {
        return parent;
    }

    public void addVariable(SObject sObject, String varName) throws ScopeException {
        if(this.localVariables.containsKey(varName)){
            throw new ScopeException("Variable already exist");
        }
        localVariables.put(varName, sObject);
    }

    public Block addIfWhile(int index) throws ScopeException {
        int blockLength = scopeLength(lines, index);
        ArrayList<Line> methodLines = new ArrayList<>(lines.subList(index, index + blockLength));
        return new Block(this, methodLines);
    }

    public void validateScopeEnd() throws ScopeException {}

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
            throw new ScopeException("Missing closing bracket");
        }
        return count;
    }
}
