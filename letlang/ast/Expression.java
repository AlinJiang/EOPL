package letlang.ast;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;

public abstract class Expression {

    public abstract RetType visit(AbstractInterpret letVisitor);
}
