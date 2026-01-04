package Variables;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SBoolean extends SObject {
    public SBoolean(String name, boolean isFinal, boolean isInitialized, String value) {
        super(name, isFinal, isInitialized, VarTypes.SBOOLEAN);
        isValidInput(value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = "^(true|false|[+-]?((\\d+\\.\\d+)|(\\.\\d+)|(\\d+\\.)|(\\d+)))$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();

    }
}
