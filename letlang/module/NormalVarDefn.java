package letlang.module;

import letlang.ast.Expression;

public class NormalVarDefn extends VarDefn {

    public Expression exp;
    
    public NormalVarDefn(String var, Expression exp) {
        super(var);
        this.exp = exp;
    }

}
