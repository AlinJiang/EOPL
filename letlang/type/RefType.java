package letlang.type;

public class RefType extends Type {

    public OptionalType underType;
    
    public RefType(OptionalType underType) {
        this.underType = underType;
    }

    @Override
    public boolean equals(OptionalType ty2) {
        if (ty2 instanceof RefType) {
            RefType refType = (RefType)ty2;
            return underType.equals(refType.underType);
        } else {
            return false;
        }
    }
    
}
