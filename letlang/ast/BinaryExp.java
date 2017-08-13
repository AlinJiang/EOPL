package letlang.ast;


public abstract class BinaryExp extends Expression {

    public Expression exp1;
    public Expression exp2;
    
    public BinaryExp(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    public String toString(String nAst) {
        StringBuilder buf = new StringBuilder();
        
        buf.append(nAst);
        buf.append("(");
        buf.append(exp1.toString());
        buf.append(" ");
        buf.append(exp2.toString());
        buf.append(")");
        
        return buf.toString();
    }
    
    
}
