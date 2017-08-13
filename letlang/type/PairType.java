package letlang.type;

public class PairType extends OptionalType {

    public OptionalType ty1;
    public OptionalType ty2;

    public PairType(OptionalType ty1, OptionalType ty2) {
        this.ty1 = ty1;
        this.ty2 = ty2;
    }
    
    public String toString() {
        return "pairof " + ty1.toString() + " * " + ty2.toString();
    }

    @Override
    public boolean equals(OptionalType ctype) {
        if (ctype instanceof PairType) {
            PairType pairType = (PairType)ctype;
            return (ty1.equals(pairType.ty1)) && 
                    (ty2.equals(pairType.ty2));
        } else {
            return false;
        }
    }
    
}
