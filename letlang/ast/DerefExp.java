package letlang.ast;

import letlang.LETTokenType;
import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class DerefExp extends UnaryExp {

    public DerefExp(Expression exp1) {
        super(exp1);
        // TODO Auto-generated constructor stub
    }
 
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
 
    public String toString() {
        return super.toString(LETTokenType.DEREF);
    }
    
}
