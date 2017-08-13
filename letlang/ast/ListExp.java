package letlang.ast;

import java.util.List;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class ListExp extends Expression {

    public List<Expression> exps;
    
    public ListExp(List<Expression> exps) {
        this.exps = exps;
    }
    
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("list(");
        
        for (int i = 0; i < exps.size(); ++i) {
            if (i > 0) {
                buf.append(",");
            }
            buf.append(exps.get(i).toString());
        }
        
        buf.append(")");
        
        return buf.toString();
    }
 
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
