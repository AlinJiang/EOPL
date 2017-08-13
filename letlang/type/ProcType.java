package letlang.type;

import java.util.List;

public class ProcType extends Type {

    public List<OptionalType> argTypes;
    public OptionalType resType;
    
    public ProcType(List<OptionalType> argTypes, OptionalType resType) {
        this.argTypes = argTypes;
        this.resType = resType;
    }
 
    public boolean equals(OptionalType ty2) {
        if (ty2 instanceof ProcType) {
            ProcType pty2 = (ProcType)ty2;
            
            if (argTypes.size() != pty2.argTypes.size()) {
                return false;
            } else {
                for (int i = 0; i < argTypes.size(); ++i) {
                    if (!argTypes.get(i).equals(pty2.argTypes.get(i))) {
                        return false;
                    }
                }
                return resType.equals(pty2.resType);
            }
        } else {
            return false;
        }
    }
    
    public String toString() {
        
        StringBuilder buf = new StringBuilder();
        
        for (int i = 0; i < argTypes.size(); ++i) {
            OptionalType type = argTypes.get(i);
            if (i > 0) {
                buf.append(" * ");
            }
            buf.append(type.toString());
        }
        
        buf.append(" -> ");
        buf.append(resType.toString());
        
        return buf.toString();
    }
    
}
