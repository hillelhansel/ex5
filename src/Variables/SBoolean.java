package Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SBoolean extends SObject {
    public SBoolean(String name, boolean isFinal, boolean isInitialized, String value) throws InvalidValueException{
        super(name, isFinal, isInitialized, VarTypes.SBOOLEAN);
        if (isInitialized && !isValidInput(value)) {
            throw new InvalidValueException(value, type);
        }
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = "^(true|false|[+-]?((\\d+\\.\\d+)|(\\.\\d+)|(\\d+\\.)|(\\d+)))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
