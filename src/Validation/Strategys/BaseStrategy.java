package Validation.Strategys;

import CodeParser.RegexPatterns;
import Validation.ValidationStrategy;
import Variables.VarTypes;


import java.util.regex.Pattern;

public abstract class BaseStrategy implements ValidationStrategy {
    protected boolean isLiteralMatchingType(String value, VarTypes expectedType) {
        String regex;
        switch (expectedType) {
            case SINT: regex = RegexPatterns.INT; break;
            case SDOUBLE: regex = RegexPatterns.DOUBLE; break;
            case SSTRING: regex = RegexPatterns.STRING; break;
            case SCHAR: regex = RegexPatterns.CHAR; break;
            case SBOOLEAN: regex = RegexPatterns.BOOLEAN; break;
            default: return false;
        }
        return Pattern.matches(regex, value);
    }
}
