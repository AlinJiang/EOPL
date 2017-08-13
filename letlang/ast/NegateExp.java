package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class NegateExp extends UnaryExp  {

    public NegateExp(Expression exp1) {
        super(exp1);
        // TODO Auto-generated constructor stub
    }

    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
