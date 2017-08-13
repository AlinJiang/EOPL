package letlang.ast;

import java.util.List;

import letlang.interpreter.AbstractInterpret;
import letlang.interpreter.RetType;
import letlang.type.OptionalType;

public class LetrecExp extends Expression {

    public List<String> fnames;
    public List<OptionalType> retTypes;
    public List<List<String>> fargs;
    public List<List<OptionalType>> argTypes;
    public List<Expression> fbodies;
    public Expression letrecBody;
    
    public LetrecExp(List<String> fnames, 
                    List<OptionalType> retTypes,
                    List<List<String>> fargs,
                    List<List<OptionalType>> argTypes,
                    List<Expression> fbodies, 
                    Expression letrecBody)
    {
        this.fnames = fnames;
        this.retTypes = retTypes;
        this.fargs = fargs;
        this.argTypes = argTypes;
        this.fbodies = fbodies;
        this.letrecBody = letrecBody;
    }
 
    public RetType visit(AbstractInterpret letVisitor) {
        return letVisitor.visit(this);
    }
    
}
