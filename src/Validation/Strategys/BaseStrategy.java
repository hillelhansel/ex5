package Validation.Strategys;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import Scopes.Scope;
import Scopes.ScopeException;
import Validation.ValidationStrategy;
import Variables.SObject;
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

    protected SObject checkVarExist(Line line, Scope scope, String varName) throws ScopeException {
        SObject targetVar = scope.getObject(varName);

        if (targetVar == null) {
            throw new ScopeException(": Variable '" + varName + "' is not defined");
        }
        return targetVar;
    }
}
