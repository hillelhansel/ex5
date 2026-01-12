package Variables;

public class SObject {
    private final boolean isFinal;
    private final String name;
    private boolean isInitialized;
    private final VarTypes type;

    public SObject(String name, boolean isFinal, VarTypes type, VarTypes incomingType) throws VariableException {
        this.name = name;
        this.isFinal = isFinal;
        this.type = type;

        if (incomingType != null) {
            validateAssignment(incomingType);
            this.isInitialized = true;
        }
        else {
            if (isFinal) {
                throw new VariableException("Final variable '" + name + "' must be initialized");
            }
            this.isInitialized = false;
        }
    }

    public void tryAssign(VarTypes incomingType) throws VariableException {
        if (isFinal && isInitialized) {
            throw new VariableException("Cannot assign a value to final variable '" + name + "'");
        }
        validateAssignment(incomingType);
        this.isInitialized = true;
    }

    private void validateAssignment(VarTypes incomingType) throws VariableException {
        if (!type.isTypeCompatible(incomingType, this.type)) {
            throw new VariableException("Type mismatch: cannot assign " + incomingType + " to " + type);
        }
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