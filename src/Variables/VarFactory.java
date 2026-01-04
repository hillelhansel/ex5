package Variables;

public class VarFactory {
    private VarFactory() {
    }

    public SObject getObject(String name, boolean isFinal, boolean isInitialized, VarTypes type, String value) {
        switch (type) {
            case SSTRING:
                return new SString(name, isFinal, isInitialized, value);
            case SINT:
                return new SInt(name, isFinal, isInitialized, value);
            case SBOOLEAN:
                return new SBoolean(name, isFinal, isInitialized, value);
            case SCHAR:
                return new SChar(name, isFinal, isInitialized, value);
            case SDOUBLE:
                return new SDouble(name, isFinal, isInitialized, value);
            default:
                return null;
        }
    }
}
