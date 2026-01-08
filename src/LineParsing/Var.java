package LineParsing;

public class Var {
    private final String name;
    private final String value;

    public Var(String name, String assignment) {
        this.name = name;
        this.value = assignment;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
