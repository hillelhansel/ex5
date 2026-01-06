package Variables;

import CodeParser.RegexPatterns;

import java.util.regex.Matcher;
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
        if (!isValidInput(value)) {
            throw new VariableException("invalid input");
        }
        if(value == null) {
            this.isInitialized = false;
        }
    }

    public boolean isValidInput(String value) throws VariableException {
        String regex;
        switch(type){
            case SCHAR -> regex = RegexPatterns.CHAR;
            case SINT -> regex = RegexPatterns.INT;
            case SBOOLEAN ->  regex = RegexPatterns.BOOLEAN;
            case SSTRING -> regex = RegexPatterns.STRING;
            case SDOUBLE ->  regex = RegexPatterns.DOUBLE;
            default -> throw new VariableException("Unknown type");
        }
        Matcher matcher = Pattern.compile(regex).matcher(value);
        return matcher.matches();
    }

    public void setValue(String value) throws VariableException {
        if(!isValidInput(value)){
            throw new VariableException("invalid input");
        }
        if(isFinal){
            throw new VariableException("cannot set final value");
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
}
