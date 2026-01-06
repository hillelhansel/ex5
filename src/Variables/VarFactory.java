package Variables;

public class VarFactory {
    public SObject getObject(String name, boolean isFinal, VarTypes type, String value) throws InvalidValueException {
        switch (type) {
            case SSTRING:
                return new SString(name, isFinal, value);
            case SINT:
                return new SInt(name, isFinal, value);
            case SBOOLEAN:
                return new SBoolean(name, isFinal, value);
            case SCHAR:
                return new SChar(name, isFinal, value);
            case SDOUBLE:
                return new SDouble(name, isFinal, value);
            default:
                return null;
        }
    }
}
