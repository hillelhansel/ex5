package Variables;

import CodeParser.RegexPatterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SChar extends SObject {
    public SChar(String name, boolean isFinal, String value) throws InvalidValueException {
        super(name, isFinal, VarTypes.SCHAR, value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = RegexPatterns.CHAR;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();

    }
}
