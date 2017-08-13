package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class CarExp extends UnaryExp {

    public CarExp(Expression exp1) {
        super(exp1);
        // TODO Auto-generated constructor stub
    }
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
}
