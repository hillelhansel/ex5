package Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SString extends SObject{
    public SString(String name, boolean isFinal, boolean isInitialized, String value) throws InvalidValueException {
        super(name, isFinal, isInitialized, VarTypes.SSTRING);
        if (isInitialized && !isValidInput(value)) {
            throw new InvalidValueException(value, type);
        }
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = "^\"([^\"]+)\"$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
