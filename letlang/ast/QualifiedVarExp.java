package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public class QualifiedVarExp extends Expression {
    // from Identifier take Identifier 
    public String fromVar;
    public String takeVar;
    
    public QualifiedVarExp(String fromVar, String takeVar) {
        this.fromVar = fromVar;
        this.takeVar = takeVar;
    }
    
    public String toString() {
        return "(qualified-var-exp " + fromVar + " " + takeVar + ")";
    }

    @Override
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
