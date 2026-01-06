package LineParsing;

import Variables.VarTypes;

public class MethodParameter {
    public final boolean isFinal;
    public final VarTypes type;
    public final String name;

    public MethodParameter(boolean isFinal, VarTypes type, String name) {
        this.isFinal = isFinal;
        this.type = type;
        this.name = name;
    }
}