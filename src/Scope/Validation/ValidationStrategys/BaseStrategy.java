package Scope.Validation.ValidationStrategys;

import CodeParser.RegexPatterns;
import Scope.Scope;
import Scope.ScopeException;
import Scope.Validation.ValidationStrategy;
import Variables.SObject;
import Variables.VarTypes;


import java.util.regex.Pattern;

public abstract class BaseStrategy implements ValidationStrategy {
    protected void validateValueByType(Scope scope, String valueExpr, VarTypes expectedType) throws ScopeException {
        SObject sourceVar = scope.getObject(valueExpr);

        if (sourceVar != null) {
            if (!sourceVar.isInitialized()) {
                throw new ScopeException(": Variable '" + valueExpr + "' is not initialized");
            }
            if (!isTypeCompatible(sourceVar.getVarType(), expectedType)) {
                throw new ScopeException("Type mismatch. Expected " + expectedType + " but got " + sourceVar.getVarType());
            }
        } else {
            if (!isLiteralMatchingType(valueExpr, expectedType)) {
                throw new ScopeException(": Value '" + valueExpr + "' is not valid for type " + expectedType);
            }
        }
    }

    protected boolean isTypeCompatible(VarTypes sourceType, VarTypes expectedType) {
        if (sourceType == expectedType) {
            return true;
        }

        if (expectedType == VarTypes.SDOUBLE && sourceType == VarTypes.SINT) {
            return true;
        }

        if (expectedType == VarTypes.SBOOLEAN) {
            return sourceType == VarTypes.SINT || sourceType == VarTypes.SDOUBLE;
        }

        return false;
    }

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

    protected SObject checkVarExist(Scope scope, String varName) throws ScopeException {
        SObject targetVar = scope.getObject(varName);

        if (targetVar == null) {
            throw new ScopeException(": Variable '" + varName + "' is not defined");
        }
        return targetVar;
    }
}
