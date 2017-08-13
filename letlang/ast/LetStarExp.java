package letlang.ast;

import java.util.List;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;


public class LetStarExp extends Expression {

    public List<String> vars;
    public List<Expression> exps;
    public Expression body;
    
    public LetStarExp(List<String> vars, List<Expression> exps, Expression body) {
        this.vars = vars;
        this.exps = exps;
        this.body = body;
    }
    
    public String toString() {
        
        StringBuilder buf = new StringBuilder();
        buf.append("let* {");
        
        for (int i = 0; i < vars.size(); ++i) {
            buf.append(vars.get(i));
            buf.append(" = ");
            buf.append(exps.get(i).toString());
            buf.append("\n");
        }
        buf.append("} in ");
        buf.append(body.toString());
        
        return buf.toString();
    }
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
}
