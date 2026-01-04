package Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SInt extends SObject{

    public SInt(String name, boolean isFinal, boolean isInitialized, String value) {
        super(name, isFinal, isInitialized, VarTypes.SINT);
        isValidInput(value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = "^(\\d+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();

    }
}
