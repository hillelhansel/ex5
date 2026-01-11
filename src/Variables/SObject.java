package Variables;

public class SObject {
    private final boolean isFinal;
    private final String name;
    private boolean isInitialized;
    private final VarTypes type;

    public SObject(String name, boolean isFinal, VarTypes type, boolean isInitialized) throws VariableException {
        this.name = name;
        this.isFinal = isFinal;
        this.type = type;
        this.isInitialized = isInitialized;

        if (isFinal && isInitialized) {
            throw new VariableException("Final variable '" + name + "' must be initialized");
        }
    }

    public void tryAssign(VarTypes incomingType) throws VariableException {
        if (isFinal && isInitialized) {
            throw new VariableException("Cannot assign a value to final variable '" + name + "'");
        }

        if (!type.isTypeCompatible(type, incomingType)) {
            throw new VariableException("Type mismatch: cannot assign " + incomingType + " to " + type);
        }

        this.isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public VarTypes getVarType(){
        return type;
    }

    public String getName() {
        return name;
    }
}