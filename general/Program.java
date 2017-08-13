package general;

import java.util.List;

import letlang.ast.Expression;
import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;
import letlang.module.ModuleDefn;

public class Program {

    public List<ModuleDefn> mDefs;
    public Expression exp;
    
    public Program(List<ModuleDefn> mDefs, Expression exp) {
        this.mDefs = mDefs;
        this.exp = exp;
    }
    
    public Expression getExp() {
        return exp;
    }
    
    public String toString() {
        return "a-program(" + mDefs.toString() + "\n" + exp.toString() + ")";
    }
    
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
