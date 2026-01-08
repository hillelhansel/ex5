package Variables;

public enum VarTypes {
    SINT("int"),
    SDOUBLE("double"),
    SSTRING("String"),
    SBOOLEAN("boolean"),
    SCHAR("char");

    private final String typeName;

    VarTypes(String typeName) {
        this.typeName = typeName;
    }

    public static VarTypes fromString(String typeStr) throws VariableException {
        for (VarTypes type : VarTypes.values()) {
            if (type.typeName.equals(typeStr)) {
                return type;
            }
        }
        throw new VariableException("Unknown type: " + typeStr);
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}