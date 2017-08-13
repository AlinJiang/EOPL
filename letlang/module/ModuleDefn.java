package letlang.module;

public class ModuleDefn {
        // module Identifier interface Iface body ModuleBody
        public String var;
        public Iface expectedIface;
        public ModuleBody mBody;
        
        public ModuleDefn(String var, Iface expectedIface, ModuleBody mBody) {
            this.var = var;
            this.expectedIface = expectedIface;
            this.mBody = mBody;
        }
        
}
