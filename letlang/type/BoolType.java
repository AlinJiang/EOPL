package letlang.type;

public class BoolType extends Type {

    public String toString() {
        return "bool";
    }
    
    @Override
    public boolean equals(OptionalType ty2) {
        if (ty2 instanceof BoolType) {
            return true;
        } else {
            return false;
        }
    }
    
}
