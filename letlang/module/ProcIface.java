package letlang.module;

public class ProcIface extends Iface {

    // ((Identifier : Iface) => Iface)
    public String var;
    public Iface pIface;
    public Iface rIface;
    
    public ProcIface(String var, Iface pIface, Iface rIface) {
        this.var = var;
        this.pIface = pIface;
        this.rIface = rIface;
    }
    
}
