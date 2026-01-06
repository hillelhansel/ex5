package Variables;

import CodeParser.RegexPatterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SInt extends SObject{

    public SInt(String name, boolean isFinal, String value) throws InvalidValueException {
        super(name, isFinal, VarTypes.SINT, value);
    }

    @Override
    public boolean isValidInput(String input) {
        String regex = RegexPatterns.INT;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
