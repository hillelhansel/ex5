package object;

public class SObject {
    private final boolean isFinal;
    private final String name;
    private boolean isInitialized;
    private final ObjectType type;

    public SObject(String name,
                   boolean isFinal,
                   ObjectType type,
                   ObjectType incomingType) throws ObjectException {
        this.name = name;
        this.isFinal = isFinal;
        this.type = type;

        if (incomingType != null) {
            validateAssignment(incomingType);
            this.isInitialized = true;
        }
        else {
            if (isFinal) {
                throw new ObjectException("Final variable '" + name + "' must be initialized");
            }
            this.isInitialized = false;
        }
    }

    public String getName() {
        return name;
    }

    public void tryAssign(ObjectType incomingType) throws ObjectException {
        if (isFinal && isInitialized) {
            throw new ObjectException("Cannot assign a value to final variable '" + name + "'");
        }
        validateAssignment(incomingType);
        this.isInitialized = true;
    }

    public ObjectType getTypeIfInitialized() throws ObjectException {
        if (!isInitialized) {
            throw new ObjectException("Variable '" + name + "' is not initialized");
        }
        return type;
    }

    private void validateAssignment(ObjectType incomingType) throws ObjectException {
        if (!type.isTypeCompatible(incomingType, this.type)) {
            throw new ObjectException("Type mismatch: cannot assign " + incomingType + " to " + type);
        }
    }
}