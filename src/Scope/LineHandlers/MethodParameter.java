package Scope.LineHandlers;

import Variables.VarTypes;

public class MethodParameter {
    private final boolean isFinal;
    private final VarTypes type;
    private final String name;

    public MethodParameter(boolean isFinal, VarTypes type, String name) {
        this.isFinal = isFinal;
        this.type = type;
        this.name = name;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public VarTypes getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}