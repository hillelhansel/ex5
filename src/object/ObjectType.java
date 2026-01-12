package object;

import syntax.RegexPatterns;

import java.util.regex.Pattern;

public enum ObjectType {
    SINT("int"),
    SDOUBLE("double"),
    SSTRING("String"),
    SBOOLEAN("boolean"),
    SCHAR("char");

    private final String typeName;

    ObjectType(String typeName) {
        this.typeName = typeName;
    }

    public static ObjectType fromString(String typeStr) throws ObjectException {
        for (ObjectType type : ObjectType.values()) {
            if (type.typeName.equals(typeStr)) {
                return type;
            }
        }
        throw new ObjectException("Unknown type: " + typeStr);
    }

    public boolean isTypeCompatible(ObjectType sourceType, ObjectType expectedType) {
        if (sourceType == expectedType) {
            return true;
        }

        if (expectedType == ObjectType.SDOUBLE && sourceType == ObjectType.SINT) {
            return true;
        }

        if (expectedType == ObjectType.SBOOLEAN) {
            return sourceType == ObjectType.SINT || sourceType == ObjectType.SDOUBLE;
        }

        return false;
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