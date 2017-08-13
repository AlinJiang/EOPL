package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class SetdynamicExp extends Expression {

    public String var;
    public Expression exp1;
    public Expression body;
    
    public SetdynamicExp(String var, Expression exp1, Expression body) {
        this.var = var;
        this.exp1 = exp1;
        this.body = body;
    }
    
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
}
