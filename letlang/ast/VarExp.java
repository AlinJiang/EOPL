package letlang.ast;

import letlang.environment.Environment;
import letlang.expval.ExpVal;
import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class VarExp extends Expression {

    public String var;
    
    public VarExp(String var) {
        this.var = var;
    }
    
    public String toString() {
        return "var-exp(" + var + ")";
    }
    
    public ExpVal interpret(Environment env) {
        return env.findVal(var);
    }
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
