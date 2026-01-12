package scope.LineHandlers;

import object.ObjectType;

public class MethodParameter {
    private final boolean isFinal;
    private final ObjectType type;
    private final String name;

    public MethodParameter(boolean isFinal, ObjectType type, String name) {
        this.isFinal = isFinal;
        this.type = type;
        this.name = name;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public ObjectType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}