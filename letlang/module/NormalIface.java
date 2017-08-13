package letlang.module;

import java.util.List;

public class NormalIface extends Iface {
    // [ {Decl}* ]
    public List<VarDecl> decls;
    
    public NormalIface(List<VarDecl> decls) {
        this.decls = decls;
    }
}
