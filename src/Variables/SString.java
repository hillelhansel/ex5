package Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SString extends SObject{
    public SString(String name, boolean isFinal, boolean isInitialized, String value) {
        super(name, isFinal, isInitialized, VarTypes.SSTRING);
        isValidInput(value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = "^\"([^\"]+)\"$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();

    }
}
