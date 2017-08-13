package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;
import letlang.type.OptionalType;

public class EmptylistExp extends Expression {
    
    public OptionalType ty;
    
    public EmptylistExp(OptionalType ty) {
        this.ty = ty;
    }

    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
