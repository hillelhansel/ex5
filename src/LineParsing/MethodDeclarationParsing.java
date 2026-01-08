package LineParsing;

import CodeParser.Line;
import Variables.VarTypes;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class MethodDeclarationParsing extends LineParsing {
    private String methodName;
    private ArrayList<MethodParameter> parameters;

    public MethodDeclarationParsing(Line line) throws IllegalCodeException {
        super(line);
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<MethodParameter> getParameters() {
        return parameters;
    }

    @Override
    public void parse() throws IllegalCodeException {
        this.methodName = extractNameBeforeBrackets(content);
        String paramsContent = extractContentInsideBrackets(content);

        this.parameters = new ArrayList<>();
        ArrayList<String> paramStrings = splitByComma(paramsContent);

        for (String paramStr : paramStrings) {
            Matcher m = getHeaderMatcher(paramStr);

            boolean IsFinal = (m.group(1) != null);
            VarTypes Type = VarTypes.fromString(m.group(2));

            String Name = paramStr.substring(m.end()).trim();

            this.parameters.add(new MethodParameter(IsFinal, Type, Name));
        }
    }
}