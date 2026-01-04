package Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SChar extends SObject {
    public SChar(String name, boolean isFinal, boolean isInitialized, String value) {
        super(name, isFinal, isInitialized, VarTypes.SCHAR);
        isValidInput(value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = "^'([^'])'$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();

    }
}
