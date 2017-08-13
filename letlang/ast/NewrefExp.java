package letlang.ast;

import letlang.LETTokenType;
import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class NewrefExp extends UnaryExp {

    public NewrefExp(Expression exp1) {
        super(exp1);
        // TODO Auto-generated constructor stub
    }

    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
    public String toString() {
        return super.toString(LETTokenType.NEWREF);
    }
    
}
