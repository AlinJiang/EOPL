package letlang.type;

public class ListType extends Type {

    public OptionalType ty;
    
    public ListType(OptionalType ty) {
        this.ty = ty;
    }

    @Override
    public boolean equals(OptionalType ty2) {
        if (ty2 instanceof ListType) {
            ListType ltype = (ListType)ty2;
            return ty.equals(ltype.ty);
        } else {
            return false;
        }
    }
    
    public String toString() {
        return "listof " + ty.toString();
    }
    
}
