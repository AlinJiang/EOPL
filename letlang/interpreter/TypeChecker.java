package letlang.interpreter;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import general.Program;
import letlang.ast.*;
import letlang.module.*;
import letlang.type.*;

public class TypeChecker implements AbstractInterpret {

    public Map<String, OptionalType> typeEnv;
    public Map<String, OptionalType> funcEnv;
    public Map<String, Map<String, OptionalType>> moduleEnv;
  //  public Map<String, OptionalType> storeEnv;
    
    public static BoolType BOOLTYPE;
    public static IntType INTTYPE;
    public static VoidType VOIDTYPE;
    
    public TypeChecker() {
        this.typeEnv = new TreeMap<String, OptionalType>();
        typeEnv.put("i", INTTYPE);
        typeEnv.put("v", INTTYPE);
        typeEnv.put("x", INTTYPE);
        
        this.funcEnv = new TreeMap<String, OptionalType>();
        this.moduleEnv = new TreeMap<>();
        
        BOOLTYPE = new BoolType();
        INTTYPE = new IntType();
        VOIDTYPE = new VoidType();
    }
    
    public boolean checkTypeEquality(OptionalType ty1, OptionalType ty2, Expression exp) {
        if (ty1 == null || !ty1.equals(ty2)) {
            System.out.println(exp.toString() + " not equal type");
            return false;
        } else {
            return true;
        }
    }
    
    public RetType visitBinaryExp(BinaryExp exp, OptionalType typeSort) {
        OptionalType ty1 = (OptionalType)exp.exp1.visit(this);
        OptionalType ty2 = (OptionalType)exp.exp2.visit(this);
        
        checkTypeEquality(ty1, typeSort, exp.exp1);
        checkTypeEquality(ty2, typeSort, exp.exp2);
        
        return typeSort;
    }
    
    @Override
    public RetType visit(AddExp exp) {
        return visitBinaryExp(exp, INTTYPE);
    }

    @Override
    public RetType visit(AssignExp exp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RetType visit(BeginExp exp) {
        Expression head = exp.head;
        List<Expression> tail = exp.tail;
        
        if (tail.size() == 0) {
            return head.visit(this);
        } else {
            return tail.get(tail.size() - 1).visit(this);
        }
    }

    @Override
    public RetType visit(CallExp exp) {
        Expression rator = exp.rator;
        List<Expression> rands = exp.rands;
        
        ProcType ratorType = (ProcType)rator.visit(this);
        List<OptionalType> argTypes = ratorType.argTypes;
        
        int len = rands.size();
        for (int i = 0; i < len; ++i) {
            Expression rand = rands.get(i);
            OptionalType argType = argTypes.get(i);
            OptionalType randType = (OptionalType)rand.visit(this);
            checkTypeEquality(argType, randType, rand);
        }
        
        return ratorType.resType;
    }

    @Override
    public RetType visit(CarExp exp) {
        ListType ty = (ListType)exp.exp1.visit(this);
        
        return ty.ty;
    }

    @Override
    public RetType visit(CdrExp exp) {
        ListType ty = (ListType)exp.exp1.visit(this);
        
        return ty.ty;
    }

    @Override
    public RetType visit(CondExp exp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RetType visit(ConstExp exp) {
        return INTTYPE;
    }

    @Override
    public RetType visit(ConsExp exp) {
        OptionalType htype = (OptionalType)exp.exp1.visit(this);
        OptionalType ttype = (OptionalType)exp.exp2.visit(this);
        
        if (ttype instanceof ListType) {
            ListType lstype = (ListType)ttype;
            checkTypeEquality(htype, lstype.ty, exp);
            return new ListType(htype);
        } else {
            checkTypeEquality(INTTYPE, BOOLTYPE, exp);
            return null;
        }
    }

    @Override
    public RetType visit(DerefExp exp) {
        
        OptionalType expType = (RefType)exp.exp1.visit(this);
        
        if (expType instanceof RefType) {
            RefType refType = (RefType)expType;
            
            return refType.underType;
        } else {
            checkTypeEquality(INTTYPE, BOOLTYPE, exp);
            return null;
        }
        
    }

    @Override
    public RetType visit(DiffExp exp) {
        return visitBinaryExp(exp, INTTYPE);
    }

    @Override
    public RetType visit(EmptylistExp exp) {
        return new ListType(exp.ty);
    }

    @Override
    public RetType visit(EqualQuestionExp exp) {
        return visitBinaryExp(exp, BOOLTYPE);
    }

    @Override
    public RetType visit(GreaterQuestionExp exp) {
        return visitBinaryExp(exp, BOOLTYPE);
    }

    @Override
    public RetType visit(IfExp exp) {
        Expression guard = exp.guard;
        OptionalType guardType = (OptionalType)guard.visit(this);
        
        checkTypeEquality(guardType, BOOLTYPE, guard);
        
        OptionalType thenType = (OptionalType)exp.thenbr.visit(this);
        OptionalType elseType = (OptionalType)exp.elsebr.visit(this);
        
        checkTypeEquality(thenType, elseType, exp);
        
        return thenType;
    }

    @Override
    public RetType visit(LessQuestionExp exp) {
        return visitBinaryExp(exp, BOOLTYPE);
    }

    @Override
    public RetType visit(LetExp exp) {
        
        List<String> vars = exp.vars;
        List<Expression> exps = exp.exps;
        Expression body = exp.body;
        
        Map<String, OptionalType> oldTypes = new TreeMap<String, OptionalType>();
        Map<String, OptionalType> newTypes = new TreeMap<String, OptionalType>();
        int len = vars.size();        
        for (int i = 0; i < len; ++i) {
            String var = vars.get(i);
            OptionalType type = (OptionalType) exps.get(i).visit(this);
            if (typeEnv.containsKey(var)) {
                oldTypes.put(var, typeEnv.get(var));
            }
            newTypes.put(var, type);
        }
        
        for (String var : newTypes.keySet()) {
            typeEnv.put(var, newTypes.get(var));
        }
        
        RetType bodyType = body.visit(this);
        
        for (String var : newTypes.keySet()) {
            typeEnv.remove(var);
        }
        for (String var : oldTypes.keySet()) {
            typeEnv.put(var, oldTypes.get(var));
        }
        
        return bodyType;
    }

    @Override
    public RetType visit(LetmutableExp exp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RetType visit(LetrecExp exp) {
        
        List<List<OptionalType>> argTypes = exp.argTypes;
        List<List<String>> fargs = exp.fargs;
        List<Expression> fbodies = exp.fbodies;
        List<OptionalType> retTypes = exp.retTypes;
        List<String> fnames = exp.fnames;
        Expression body = exp.letrecBody;
        
        // put all function types
        Map<String, OptionalType> oldFuncEnv = this.funcEnv;
        this.funcEnv = new TreeMap<String, OptionalType>(this.funcEnv);
        
        int len = argTypes.size();
        // put all return types for functions for evaluation
        for (int i = 0; i < len; ++i) {
            String fname = fnames.get(i);
            OptionalType retType = retTypes.get(i);
            List<OptionalType> argType = argTypes.get(i);
            funcEnv.put(fname, new ProcType(argType, retType));
        }
        
        for (int i = 0; i < len; ++i) {
            Expression fbody = fbodies.get(i);
            List<String> farg = fargs.get(i);
            List<OptionalType> argType = argTypes.get(i);
            OptionalType retType = retTypes.get(i);
            Map<String, OptionalType> oldEnv = this.typeEnv;
            this.typeEnv = new TreeMap<String, OptionalType>(this.typeEnv);
            // put all argument types in the environment
            for (int j = 0; j < farg.size(); ++j) {
                typeEnv.put(farg.get(j), argType.get(j));
            }
            // evaluate the type in the argument environment
            OptionalType fbodyType = (OptionalType)fbody.visit(this);
            checkTypeEquality(fbodyType, retType, fbody);
            
            this.typeEnv = oldEnv;
        }
        
        RetType bodyType = body.visit(this);
        
        this.funcEnv = oldFuncEnv;
        
        return bodyType;
    }

    @Override
    public RetType visit(LetStarExp exp) {
        List<String> vars = exp.vars;
        List<Expression> exps = exp.exps;
        Expression body = exp.body;
        
        Map<String, OptionalType> oldTypes = new TreeMap<String, OptionalType>();
        int len = vars.size();        
        for (int i = 0; i < len; ++i) {
            String var = vars.get(i);
            OptionalType type = (OptionalType) exps.get(i).visit(this);
            if (typeEnv.containsKey(var)) {
                oldTypes.put(var, typeEnv.get(var));
            }
            typeEnv.put(var, type);
        }
        
        
        RetType bodyType = body.visit(this);
        
        for (String var : vars) {
            typeEnv.remove(var);
        }
        for (String var : oldTypes.keySet()) {
            typeEnv.put(var, oldTypes.get(var));
        }
        
        return bodyType;
    }

    @Override
    public RetType visit(ListExp exp) {
        
        List<Expression> exps = exp.exps;
        OptionalType ty = null;
        for (Expression e : exps) {
            if (ty == null) {
                ty = (OptionalType)e.visit(this);
            } else {
                OptionalType curr = (OptionalType)e.visit(this);
                checkTypeEquality(ty, curr, e);
            }
        }
        
        return new ListType(ty);
    }

    @Override
    public RetType visit(MulExp exp) {
        return visitBinaryExp(exp, INTTYPE);
    }

    @Override
    public RetType visit(NegateExp exp) {
        OptionalType ty1 = (OptionalType)exp.exp1.visit(this);
        
        checkTypeEquality(ty1, INTTYPE, exp.exp1);
        
        return INTTYPE;
    }

    @Override
    public RetType visit(NewrefExp exp) {
        OptionalType underType = (OptionalType)exp.exp1.visit(this);
        
        return new RefType(underType);
    }

    @Override
    public RetType visit(NullQuestionExp exp) {
        OptionalType lexp = (OptionalType)exp.visit(this);
        
        if (lexp instanceof ListType) {
            return BOOLTYPE;
        } else {
            checkTypeEquality(INTTYPE, BOOLTYPE, exp);
            return null;
        }
    }

    @Override
    public RetType visit(ProcExp exp) {
        List<String> args = exp.vars;
        List<OptionalType> argTypes = exp.argTypes;
        Expression body = exp.body;
        
        Map<String, OptionalType> oldEnv = this.typeEnv;
        this.typeEnv = new TreeMap<String, OptionalType>(this.typeEnv);
        
        int len = args.size();
        for (int i = 0; i < len; ++i) {
            String var = args.get(i);
            OptionalType varType = argTypes.get(i);
            typeEnv.put(var, varType);
        }
        
        OptionalType resType = (OptionalType)body.visit(this);
        
        this.typeEnv = oldEnv;
        
        return new ProcType(argTypes, resType);
    }

    @Override
    public RetType visit(QuotientExp exp) {
        return visitBinaryExp(exp, INTTYPE);
    }

    @Override
    public RetType visit(SetdynamicExp exp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RetType visit(SetrefExp exp) {
        
        OptionalType ltype = (OptionalType)exp.exp1.visit(this);
        OptionalType rtype = (OptionalType)exp.exp2.visit(this);
        
        checkTypeEquality(ltype, rtype, exp);
        
        return VOIDTYPE;
    }

    @Override
    public RetType visit(TraceprocExp exp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RetType visit(UnpackExp exp) {
        List<String> vars = exp.vars;
        OptionalType ls = (OptionalType) exp.exp1.visit(this);
        
        if (ls instanceof ListType) {
            Map<String, OptionalType> oldTypeEnv = this.typeEnv;
            this.typeEnv = new TreeMap<String, OptionalType>(oldTypeEnv);
            ListType ltype = (ListType)ls;
            for (int i = 0; i < vars.size(); ++i) {
                this.typeEnv.put(vars.get(i), ltype.ty);
            }
            RetType retType = exp.body.visit(this);
            
            this.typeEnv = oldTypeEnv;
            
            return retType;
        } else {
            checkTypeEquality(INTTYPE, BOOLTYPE, exp);
            return null;
        }
        
    }

    @Override
    public RetType visit(VarExp exp) {
        String varName = exp.var;
        if (funcEnv.containsKey(varName)) {
            return funcEnv.get(varName);
        } else if (typeEnv.containsKey(varName)) {
            return typeEnv.get(varName);
        } else {
            for (String mName : moduleEnv.keySet()) {
                Map<String, OptionalType> moduleTypeEnv = moduleEnv.get(mName);
                if (moduleTypeEnv.containsKey(varName)) {
                    return moduleTypeEnv.get(varName);
                }
            }
            return null;
        }
    }

    @Override
    public RetType visit(ZeroQuestionExp exp) {
        // zero?(Expression)
        Expression exp1 = exp.exp1;
        OptionalType expType = (OptionalType)exp1.visit(this);
        
        checkTypeEquality(expType, INTTYPE, exp1);
        
        return BOOLTYPE;
    }

    @Override
    public RetType visit(QualifiedVarExp exp) {
        String mName = exp.fromVar;
        String vName = exp.takeVar;
        
        return moduleEnv.get(mName).get(vName);
    }

    @Override
    public RetType visit(NewpairExp exp) {
        OptionalType ltype = (OptionalType)exp.exp1.visit(this);
        OptionalType rtype = (OptionalType)exp.exp2.visit(this);
        
        return new PairType(ltype, rtype);
    }

    @Override
    public RetType visit(UnpairExp exp) {
        Map<String, OptionalType> oldTypeEnv = this.typeEnv;
        typeEnv = new TreeMap<String, OptionalType>(this.typeEnv);
        
        PairType pairType = (PairType)exp.pairExp.visit(this);
        typeEnv.put(exp.left, pairType.ty1);
        typeEnv.put(exp.right, pairType.ty2);
        
        RetType bodyType = exp.body.visit(this);
        
        this.typeEnv = oldTypeEnv;
        
        return bodyType;
    }

    @Override
    public RetType visit(Program prog) {
        
        List<ModuleDefn> mDefns = prog.mDefs;
        
        for (ModuleDefn m : mDefns) {
            this.moduleEnv.put(m.var, new TreeMap<>());
        }
        
        for (ModuleDefn m : mDefns) {
            visitModuleDefn(m);
        }
        
        return prog.exp.visit(this);
    }
    
    private int containsDefn(List<VarDefn> defns, String var) {
        for (int i = 0; i < defns.size(); ++i) {
            VarDefn defn = defns.get(i);
            if (defn.var.equals(var)) {
                return i;
            }
        }
        return -1;
    }
    
    public void visitModuleDefn(ModuleDefn m) {
        Map<String, OptionalType> moduleType = this.moduleEnv.get(m.var);
        
        List<VarDecl> decls = m.expectedIface.decls;
        List<VarDefn> defns = m.mBody.defns;
        
        for (int i = 0; i < decls.size(); ++i) {
            String var = decls.get(i).var;
            int defnPos = containsDefn(defns, var);
            if (defnPos != -1) {
                OptionalType expectedType = decls.get(i).type;
                Expression exp = defns.get(defnPos).exp;
                OptionalType expType = (OptionalType) exp.visit(this);
                checkTypeEquality(expectedType, expType, exp);
                
                moduleType.put(var, expectedType);
            } else {
                System.out.println(var + " not implemented");
                return;
            }
        }
        
        for (int i = 0; i < defns.size(); ++i) {
            String defnVar = defns.get(i).var;
            if (!moduleType.containsKey(defnVar)) {
                Expression exp = defns.get(i).exp;
                OptionalType calculatedType = (OptionalType) exp.visit(this);
                moduleType.put(defnVar, calculatedType);
            }
        }
        
    }
    
}
