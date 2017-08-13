package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class DiffExp extends BinaryExp {

    public DiffExp(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }
    
    public String toString() {
        return "diff-exp(" + exp1.toString() + " " + exp2.toString() + ")";
    }

    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
