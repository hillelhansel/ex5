package LineParsing;

import CodeParser.Line;
import CodeParser.RegexPatterns;
import main.IllegalCodeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class IfWhileParsing extends LineParsing {
    private ArrayList<String> parameters;

    public IfWhileParsing(Line line) throws IllegalCodeException {
        super(line);
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    @Override
    public void parse() {
        String insideBrackets = extractContentInsideBrackets(content);

        String[] parts = insideBrackets.split(RegexPatterns.LOGICAL_OPS);

        this.parameters = Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}