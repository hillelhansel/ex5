package Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SDouble extends SObject {
    public SDouble(String name, boolean isFinal, boolean isInitialized, String value) {
        super(name, isFinal, isInitialized, VarTypes.SDOUBLE);
        isValidInput(value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = "^[+-]?((\\d+\\.\\d+)|(\\.?\\d+)|(\\d+\\.))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
