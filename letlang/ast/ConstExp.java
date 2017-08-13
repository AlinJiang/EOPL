package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class ConstExp extends Expression {

    public int number;
    
    public ConstExp(int number) {
        this.number = number;
    }
    
    
    
    public String toString() {
        return "const-exp(" + number + ")";
    }
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
}
