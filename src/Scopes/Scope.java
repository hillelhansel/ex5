package Scopes;

import Variables.SObject;
import Variables.VarTypes;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Scope {
    protected Scope parent;
    protected final ArrayList<String> lines;
    protected final HashMap<String, SObject> localVariabels;

    public Scope(Scope parent, ArrayList<String> lines) {
        this.parent = parent;
        this.lines = lines;
        this.localVariabels = new HashMap<>();
    }

    protected boolean addVariabele(SObject sObject, String varName){
        if(this.localVariabels.containsKey(varName)){
            return false;
        }
        localVariabels.put(varName, sObject);
        return true;
    }

    private boolean checkObjectExists(String name) {
        if (localVariabels.containsKey(name)) {
            return true;
        }
        else{
            parent.checkObjectExists(name);
        }
        return false;
    }

    private SObject getObject(String name) {
        return localVariabels.get(name);
    }

    private VarTypes getVarType(String name) {
        if (checkObjectExists(name)) {
            SObject object = getObject(name);
            if (object != null){
                return object.getVarType();
            }
            return parent.getVarType(name);
        }
        return null;
    }
}
