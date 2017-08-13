package letlang.ast;

import java.util.List;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class CallExp extends Expression {

    public Expression rator;
    public List<Expression> rands;
    
    public CallExp(Expression rator, List<Expression> rands) {
        this.rands = rands;
        this.rator = rator;
    }
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(");
        buf.append(rator.toString());
        
        for (Expression exp : rands) {
            buf.append(" ");
            buf.append(exp.toString());
        }
        
        buf.append(")");
        
        return buf.toString();
    }
}
