package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class GreaterQuestionExp extends BinaryExp  {

    public GreaterQuestionExp(Expression exp1, Expression exp2) {
        super(exp1, exp2);
        // TODO Auto-generated constructor stub
    }

    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
