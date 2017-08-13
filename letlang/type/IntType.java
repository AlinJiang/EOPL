package letlang.type;

public class IntType extends Type {

    public String toString() {
        return "int";
    }
    
    public boolean equals(OptionalType ty2) {
        if (ty2 instanceof IntType) {
            return true;
        } else {
            return false;
        }
    }
    
}
