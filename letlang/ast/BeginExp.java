package letlang.ast;

import java.util.List;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class BeginExp extends Expression {

    public Expression head;
    public List<Expression> tail;
    
    public BeginExp(Expression head, List<Expression> tail) {
        this.head = head;
        this.tail = tail;
    }
    
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(begin ");
        buf.append(head.toString());
        buf.append("\n");
        for (Expression exp : tail) {
            buf.append(", ");
            buf.append(exp.toString());
        }
        buf.append(")");
        
        return buf.toString();
    }
}
