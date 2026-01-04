package Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SDouble extends SObject {
    public SDouble(String name, boolean isFinal, boolean isInitialized, String value) throws InvalidValueException {
        super(name, isFinal, isInitialized, VarTypes.SDOUBLE);
        if (isInitialized && !isValidInput(value)) {
            throw new InvalidValueException(value, type);
        }    }

    @Override
    public boolean isValidInput(String input) {
        String regex = "^[+-]?((\\d+\\.\\d+)|(\\.?\\d+)|(\\d+\\.))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
