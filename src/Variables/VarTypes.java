package Variables;

import CodeParser.RegexPatterns;

import java.util.regex.Pattern;

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

    public boolean isValidValue(String value) {
        String regex;
        switch (this) {
            case SINT:
                regex = RegexPatterns.INT;
                break;
            case SDOUBLE:
                regex = RegexPatterns.DOUBLE;
                break;
            case SSTRING:
                regex = RegexPatterns.STRING;
                break;
            case SBOOLEAN:
                regex = RegexPatterns.BOOLEAN;
                break;
            case SCHAR:
                regex = RegexPatterns.CHAR;
                break;
            default:
                return false;
        }
        return Pattern.matches(regex, value);
    }

    @Override
    public String toString() {
        return this.typeName;
    }
}