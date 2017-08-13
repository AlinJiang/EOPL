package letlang.module;


public abstract class VarDecl {
    // Identifier : Type
    public String var;
    
    public VarDecl(String var) {
        this.var = var;
    }
}
