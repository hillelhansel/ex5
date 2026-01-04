package Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SInt extends SObject{

    public SInt(String name, boolean isFinal, boolean isInitialized, String value) throws InvalidValueException {
        super(name, isFinal, isInitialized, VarTypes.SINT);
        if (isInitialized && !isValidInput(value)) {
            throw new InvalidValueException(value, type);
        }    }

    @Override
    public boolean isValidInput(String input) {
        String regex = "^[+-]?(\\d+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
