package Variables;

import CodeParser.RegexPatterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SString extends SObject{
    public SString(String name, boolean isFinal, String value) throws InvalidValueException {
        super(name, isFinal, VarTypes.SSTRING, value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = RegexPatterns.STRING;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
