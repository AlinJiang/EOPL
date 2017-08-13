package letlang.type;

public class QualifiedType extends Type {

    public String mName;
    public String tName;
    
    public QualifiedType(String mName, String tName) {
        this.mName = mName;
        this.tName = tName;
    }

    @Override
    public boolean equals(OptionalType ty2) {
        if (ty2 instanceof QualifiedType) {
            QualifiedType qualifiedType = (QualifiedType)ty2;
            return (mName.equals(qualifiedType.mName)) && 
                    (tName.equals(qualifiedType.tName));
        } else {
            return false;
        }
    }
    
}
