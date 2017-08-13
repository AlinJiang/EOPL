package letlang.ast;

import java.util.List;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class UnpackExp extends Expression {

    public List<String> vars;
    public Expression exp1;
    public Expression body;
    
    public UnpackExp(List<String> vars, Expression exp1, Expression body) {
        this.vars = vars;
        this.exp1 = exp1;
        this.body = body;
    }
    
//    public String toString() {
//        
//        StringBuilder buf = new StringBuilder();
//        
//        
//    }
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
