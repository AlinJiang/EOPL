package letlang.module;

import java.util.List;

public class NormalModuleBody extends ModuleBody {
    // [ {Defn}* ]
    public List<VarDefn> defns;
    
    public NormalModuleBody(List<VarDefn> defns) {
        this.defns = defns;
    }
}
