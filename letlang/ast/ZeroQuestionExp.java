package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class ZeroQuestionExp extends UnaryExp {

    public ZeroQuestionExp(Expression exp1) {
        super(exp1);
    }
    
    public String toString() {
        return "zero?-exp(" + exp1.toString() + ")";
    }
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
}
