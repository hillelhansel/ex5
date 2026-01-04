package Variables;

abstract class SObject {
    protected final boolean isFinal;
    protected String name;
    protected boolean isInitialized;
    protected final VarTypes type;

    public SObject(String name, boolean isFinal, boolean isInitialized, VarTypes type) {
        this.name = name;
        this.isFinal = isFinal;
        this.isInitialized = isInitialized;
        this.type = type;
    }

    public abstract boolean isValidInput(String value);

    public void updateIntialiazes(){
        isInitialized = true;
    }

    public VarTypes getVarType(){
        return type;
    }

    public String getName() {
        return name;
    }
}
