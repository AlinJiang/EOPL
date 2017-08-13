package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class UnpairExp extends Expression {

    public String left;
    public String right;
    public Expression pairExp;
    public Expression body;
    
    public UnpairExp(String left, String right, Expression pairExp, Expression body) {
        this.left = left;
        this.right = right;
        this.pairExp = pairExp;
        this.body = body;
    }
    
    @Override
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }

    
}
