package letlang.ast;

import java.util.List;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class CondExp extends Expression {

    public List<Expression> condExps;
    public List<Expression> thenExps;
    
    public CondExp(List<Expression> condExps, List<Expression> thenExps) {
        this.condExps = condExps;
        this.thenExps = thenExps;
    }
    
    public String toString() {
        
        StringBuilder buf = new StringBuilder();
        buf.append("cond {\n");
        
        for (int i = 0; i < condExps.size(); ++i) {
            buf.append(condExps.get(i).toString());
            buf.append(" ==> ");
            buf.append(thenExps.get(i).toString());
            buf.append("\n");
        }
        
        buf.append("} end");
        
        return buf.toString();
    }
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
