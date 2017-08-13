package letlang.module;

public class ProcModuleBody extends ModuleBody {

    // module-proc (Identifier : Iface) ModuleBody
    public String mName;
    public Iface mType;
    public ModuleBody mBody;
    
    public ProcModuleBody(String mName, Iface mType, ModuleBody mBody) {
        this.mName = mName;
        this.mType = mType;
        this.mBody = mBody;
    }
    
}
