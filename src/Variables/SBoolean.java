package Variables;

import CodeParser.RegexPatterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SBoolean extends SObject {
    public SBoolean(String name, boolean isFinal, String value) throws InvalidValueException{
        super(name, isFinal, VarTypes.SBOOLEAN, value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = RegexPatterns.BOOLEAN;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
