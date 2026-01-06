package Variables;

public enum VarTypes {
    SSTRING,
    SINT,
    SCHAR,
    SDOUBLE,
    SBOOLEAN;

    public static VarTypes convertStringToEnum(String typeStr) {
        switch (typeStr) {
            case "int": return VarTypes.SINT;
            case "double": return VarTypes.SDOUBLE;
            case "String": return VarTypes.SSTRING;
            case "boolean": return VarTypes.SBOOLEAN;
            case "char": return VarTypes.SCHAR;
            default: throw new IllegalArgumentException("Unknown type: " + typeStr);
        }
    }
}
