package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class AssignExp extends Expression {

    public String var;
    public Expression exp1;
    
    public AssignExp(String var, Expression exp1) {
        this.var = var;
        this.exp1 = exp1;
    }
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("set(" + var + " ");
        buf.append(exp1.toString());
        buf.append(")");
        
        return buf.toString();
    }
    
}
