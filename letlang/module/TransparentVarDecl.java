package letlang.module;

import letlang.type.OptionalType;

public class TransparentVarDecl extends VarDecl {

    public OptionalType type;

    public TransparentVarDecl(String var, OptionalType type) {
        super(var);
        this.type = type;
    }
    
}
