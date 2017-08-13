package letlang.type;

public class VoidType extends Type {

    @Override
    public boolean equals(OptionalType ty2) {
        if (ty2 instanceof VoidType) {
            return true;
        } else {
            return false;
        }
    }

}
