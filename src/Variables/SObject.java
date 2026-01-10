package Variables;

public class SObject {
    private final boolean isFinal;
    private final String name;
    private boolean isInitialized = false;
    private final VarTypes type;

    public SObject(String name, boolean isFinal, VarTypes type, String value) {
        this.name = name;
        this.isFinal = isFinal;
        this.type = type;

        if (value != null) {
            this.isInitialized = true;
        }
    }

    public void assign(Object newValue) throws VariableException {

        if (isFinal && isInitialized) {
            throw new VariableException("Cannot assign to final variable");
        }

        if (!type.isValidValue(newValue)) {
            throw new VariableException(
                    "Invalid input value for type " + type
            );
        }
        this.isInitialized = true;
    }

    public VarTypes getVarType(){
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setIsInitialized(Boolean initialized) {
        isInitialized = initialized;
    }
}