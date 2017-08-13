package letlang.module;

import letlang.type.OptionalType;

public class NormalVarDecl extends VarDecl {

    public OptionalType type;
    
    public NormalVarDecl(String var, OptionalType type) {
        super(var);
        this.type = type;
    }

    
    
}
