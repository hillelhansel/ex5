package Variables;

public class SObject {
    private final boolean isFinal;
    private final String name;
    private boolean isInitialized = false;
    private final VarTypes type;

    public SObject(String name, boolean isFinal, VarTypes type, String value) throws VariableException {
        this.name = name;
        this.isFinal = isFinal;
        this.type = type;

        if (isFinal && value == null) {
            throw new VariableException("Final variable '" + name + "' must be initialized");
        }

        if (value != null) {
            this.isInitialized = true;
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

    public void setIsInitialized() {
        this.isInitialized = true;
    }
}