package letlang.interpreter;

import general.Program;
import letlang.ast.*;

public interface AbstractInterpret {

    RetType visit(AddExp exp);
    RetType visit(AssignExp exp);
    RetType visit(BeginExp exp);
    RetType visit(CallExp exp);
    RetType visit(CarExp exp);    
    RetType visit(CdrExp exp);
    RetType visit(CondExp exp);
    RetType visit(ConstExp exp);
    RetType visit(ConsExp exp);    
    RetType visit(DerefExp exp);
    RetType visit(DiffExp exp);
    RetType visit(EmptylistExp exp);    
    RetType visit(EqualQuestionExp exp);
    RetType visit(GreaterQuestionExp exp);
    RetType visit(IfExp exp);    
    RetType visit(LessQuestionExp exp);
    RetType visit(LetExp exp);
    RetType visit(LetmutableExp exp);
    RetType visit(LetrecExp exp);
    RetType visit(LetStarExp exp);
    RetType visit(ListExp exp);
    RetType visit(MulExp exp);
    RetType visit(NegateExp exp);    
    RetType visit(NewrefExp exp);
    RetType visit(NullQuestionExp exp);
    RetType visit(ProcExp exp);
    RetType visit(QuotientExp exp);    
    RetType visit(SetdynamicExp exp);
    RetType visit(SetrefExp exp);
    RetType visit(TraceprocExp exp);    
    RetType visit(UnpackExp exp);
    RetType visit(VarExp exp);
    RetType visit(ZeroQuestionExp exp);   
    RetType visit(QualifiedVarExp exp);
    RetType visit(NewpairExp exp);
    RetType visit(UnpairExp exp);
    RetType visit(Program prog);
    
    
}
