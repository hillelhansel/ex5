package Variables;

import CodeParser.RegexPatterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SDouble extends SObject {
    public SDouble(String name, boolean isFinal, String value) throws InvalidValueException {
        super(name, isFinal, VarTypes.SDOUBLE, value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = RegexPatterns.DOUBLE;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
