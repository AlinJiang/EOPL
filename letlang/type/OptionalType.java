package letlang.type;

import letlang.interpreter.RetType;

public abstract class OptionalType implements RetType {

    public abstract boolean equals(OptionalType ty2);
    
}
