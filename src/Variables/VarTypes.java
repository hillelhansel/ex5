package Variables;

import java.util.HashMap;
import java.util.Map;

public enum VarTypes {
    // מגדירים את הקבועים עם המחרוזת המתאימה להם
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
                return type; // מצאנו!
            }
        }
        throw new VariableException("Unknown type: " + typeStr);
    }

    // אופציונלי: אם תרצה להדפיס את השם היפה בחזרה
    @Override
    public String toString() {
        return this.typeName;
    }
}