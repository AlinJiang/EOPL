package letlang.module;

import letlang.type.OptionalType;

public class TypeVarDefn extends VarDefn {

    public OptionalType type;
    
    public TypeVarDefn(String var, OptionalType type) {
        super(var);
        this.type = type;
    }

}
