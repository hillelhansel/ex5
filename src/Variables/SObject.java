package Variables;

import CodeParser.RegexPatterns;
import java.util.regex.Pattern;

public class SObject {
    private final boolean isFinal;
    private final String name;
    private boolean isInitialized;
    private final VarTypes type;

    public SObject(String name, boolean isFinal, VarTypes type, String value) throws VariableException {
        this.name = name;
        this.isFinal = isFinal;
        this.type = type;

        if (value != null) {
            if (!isValidInput(value)) {
                throw new VariableException("Invalid input value for type " + type);
            }
            this.isInitialized = true;
        }
        else {
            if (isFinal) {
                throw new VariableException("Final variable '" + name + "' must be initialized");
            }
            this.isInitialized = false;
        }
    }

    public boolean isValidInput(String value) {
        if (value == null){
            return false;
        }

        String regex;
        switch(type){
            case SCHAR: regex = RegexPatterns.CHAR; break;
            case SINT: regex = RegexPatterns.INT; break;
            case SBOOLEAN: regex = RegexPatterns.BOOLEAN; break;
            case SSTRING: regex = RegexPatterns.STRING; break;
            case SDOUBLE: regex = RegexPatterns.DOUBLE; break;
            default: return false;
        }
        return Pattern.matches(regex, value);
    }

    public void setValue(String value) throws VariableException {
        if (isFinal) {
            throw new VariableException("Cannot assign a value to final variable");
        }
        if (!isValidInput(value)) {
            throw new VariableException("Invalid input value: " + value);
        }
        this.isInitialized = true;
    }

    public VarTypes getVarType(){
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setIsInitialized(Boolean initialized) {
        isInitialized = initialized;
    }
}