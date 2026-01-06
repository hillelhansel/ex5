package Variables;

public abstract class SObject {
    protected final boolean isFinal;
    protected String name;
    protected boolean isInitialized;
    protected final VarTypes type;
    protected String value;

    public SObject(String name, boolean isFinal, VarTypes type, String value) throws InvalidValueException {
        this.name = name;
        this.isFinal = isFinal;
        this.type = type;
        if (!isValidInput(value)) {
            throw new InvalidValueException(value, type);
        }
        this.value = value;
        if(value == null) {
            this.isInitialized = false;
        }
    }

    public abstract boolean isValidInput(String value);

    public void setValue(String value) {
        if(!isValidInput(value)){
            throw
        }
        if(isFinal){
            throw
        }
        this.value = value;
        this.isInitialized = true;
    }

    public void updateInitializes(){
        isInitialized = true;
    }

    public VarTypes getVarType(){
        return type;
    }

    public String getName() {
        return name;
    }
}
