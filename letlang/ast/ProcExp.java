package letlang.ast;

import java.util.List;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;
import letlang.type.OptionalType;

public class ProcExp extends Expression {

    public List<String> vars;
    public List<OptionalType> argTypes;
    public Expression body;
    
    public ProcExp(List<String> vars, List<OptionalType> argTypes, Expression body) {
        this.vars = vars;
        this.argTypes = argTypes;
        this.body = body;
    }
 
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("(proc-exp ");
        for (String var : vars) {
            buf.append(var);
            buf.append(", ");
        }
        buf.append(" with body = ");
        buf.append(body.toString());
        
        return buf.toString();
    }
    
}
