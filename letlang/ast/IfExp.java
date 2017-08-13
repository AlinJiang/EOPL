package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class IfExp extends Expression {

    public Expression guard;
    public Expression thenbr;
    public Expression elsebr;
    
    public IfExp(Expression guard, Expression thenbr, Expression elsebr) {
        this.guard = guard;
        this.thenbr = thenbr;
        this.elsebr = elsebr;
    }
    
    public String toString() {
        return "if-exp(" + guard.toString() + " " + thenbr.toString() + " " + elsebr.toString() + ")";
    }
 
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
}
