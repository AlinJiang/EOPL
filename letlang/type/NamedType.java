package letlang.type;

public class NamedType extends Type {

    public String name;
    
    public NamedType(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(OptionalType ty2) {
        if (ty2 instanceof NamedType) {
            NamedType namedType = (NamedType)ty2;
            return name.equals(namedType.name);
        } else {
            return false;
        }
    }
    
}
