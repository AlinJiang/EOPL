package letlang.ast;

import letlang.LETTokenType;

public abstract class UnaryExp extends Expression {

    public Expression exp1;
    
    public UnaryExp(Expression exp1) {
        this.exp1 = exp1;
    }

    public String toString(LETTokenType token) {
        return "(" + token + " " + exp1.toString() + ")";
    }
    
}
