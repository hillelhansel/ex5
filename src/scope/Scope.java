package scope;

import main.IllegalCodeException;
import object.ObjectType;
import object.SObject;
import syntax.Line;

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

    public ScopeType getScopeType() {
        return scopeType;
    }

    public abstract void validateLine(Line line) throws IllegalCodeException;

    public void validateScopeEnd() throws ScopeException {}

    public ObjectType resolveExpressionType(String valueExpr) throws ScopeException {
        SObject sourceVar = getObject(valueExpr);

        if (sourceVar != null) {
            try {
                return sourceVar.getTypeIfInitialized();
            } catch (IllegalCodeException e) {
                throw new ScopeException(e.getMessage());
            }
        }

        for (ObjectType type : ObjectType.values()) {
            if (type.isValidValue(valueExpr)) {
                return type;
            }
        }

        throw new ScopeException("Invalid value: " + valueExpr);
    }

    public SObject resolveObject(String name) throws ScopeException {
        SObject obj = getObject(name);
        if (obj == null) {
            throw new ScopeException(": Variable '" + name + "' is not defined");
        }
        return obj;
    }

    public int openIfWhileBlock(int index) throws IllegalCodeException {
        Block block = addIfWhile(index);
        new ScopeValidator().validate(block);
        return block.getLines().size();
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

    protected Scope getParent() {
        return parent;
    }

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

    private SObject getObject(String name) {
        if (localVariables.containsKey(name)) {
            return localVariables.get(name);
        }
        if (parent != null) {
            return parent.getObject(name);
        }
        return null;
    }
}
