package Scope.Validation.ValidationStrategys;

import Scope.Scope;
import Scope.ScopeException;
import Scope.Validation.ValidationStrategy;
import Variables.SObject;
import Variables.VarTypes;


public abstract class BaseStrategy implements ValidationStrategy {
    protected VarTypes getTypeOfExpression(Scope scope, String valueExpr) throws ScopeException {
        SObject sourceVar = scope.getObject(valueExpr);
        if (sourceVar != null) {
            if (!sourceVar.isInitialized()) {
                throw new ScopeException("Variable '" + valueExpr + "' is not initialized");
            }
            return sourceVar.getVarType();
        }

        for (VarTypes type : VarTypes.values()) {
            if (type.isValidValue(valueExpr)) {
                return type;
            }
        }
        throw new ScopeException("Invalid value: " + valueExpr);
    }
}
