package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class NewpairExp extends BinaryExp {

    public NewpairExp(Expression exp1, Expression exp2) {
        super(exp1, exp2);
        // TODO Auto-generated constructor stub
    }

    @Override
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
    public String toString() {
        return super.toString("pair-exp");
    }

    
    
}
