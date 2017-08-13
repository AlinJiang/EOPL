package letlang.interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import general.Program;
import letlang.LETTokenType;
import letlang.ast.*;
import letlang.environment.Environment;
import letlang.environment.Store;
import letlang.expval.*;
import letlang.module.*;

public class SemanticInterpreter implements AbstractInterpret {

    Environment env;
    Store store;
    int locCounter;
    Map<String, Map<String, ExpVal>> moduleEnv;
    
    public SemanticInterpreter() {
        // initialize env
        env = new Environment();
        store = new Store();
        locCounter = 0;
        moduleEnv = new TreeMap<>();
    }
    
    public SemanticInterpreter(Environment newEnv) {
        this.env = newEnv;
    }
    
    public RetType visit(Program prog) {
        List<ModuleDefn> mDefns = prog.mDefs;
        for (ModuleDefn m : mDefns) {
            this.moduleEnv.put(m.var, new TreeMap<String, ExpVal>());
        }
        
        for (ModuleDefn m : mDefns) {
            visitModuleDefn(m);
        }
        
        return prog.exp.visit(this);
    }
    
    public Map<String, ExpVal> visitModuleDefn(ModuleDefn m) {
        Map<String, ExpVal> moduleEnv = this.moduleEnv.get(m.var);
        
        List<VarDecl> decls = m.expectedIface.decls;
        List<VarDefn> defns = m.mBody.defns;
        for (int i = 0; i < defns.size(); ++i) {
            VarDefn defn = defns.get(i);
            ExpVal val = (ExpVal)defn.exp.visit(this);
            String var = defn.var;
            //System.out.println(var + ", " + val);
            moduleEnv.put(var, val);
        }
        
        return moduleEnv;
    }
    
    
    public RetType visitUnaryExp(UnaryExp exp, LETTokenType token) {
        int val1 = ((NumVal)exp.exp1.visit(this)).number;
        
        switch (token) {
        case ZERO_QUESTION:
            return new BoolVal(val1 == 0);
        case NEGATE:
            return new NumVal(-val1);
        default:
            return null;
        }
        
    }
    
    public RetType visitBinaryExp(BinaryExp exp, LETTokenType token) {
      
        int val1 = ((NumVal) exp.exp1.visit(this)).number;
        int val2 = ((NumVal) exp.exp2.visit(this)).number;
        
        switch (token) {
        case ADD:
            return new NumVal(val1 + val2);
        case MINUS:
            return new NumVal(val1 - val2);
        case MUL:
            return new NumVal(val1 * val2);
        case QUOTIENT:
            return new NumVal(val1 / val2);
        case GREATER_QUESTION:
            return new BoolVal(val1 > val2);
        case LESS_QUESTION:
            return new BoolVal(val1 < val2);
        case EQUAL_QUESTION:
            return new BoolVal(val1 == val2);
        default:
            return null;
        }
        
        
    }
    
    @Override
    public RetType visit(AddExp exp) {
        return visitBinaryExp(exp, LETTokenType.ADD);
    }

    @Override
    public RetType visit(AssignExp exp) {
        String var = exp.var;
        ExpVal val = (ExpVal)exp.exp1.visit(this);
        
        int loc = ((RefVal)env.findVal(var)).loc;
        store.modifyLocation(loc, val);
        
        return new NumVal(23);
    }

    @Override
    public RetType visit(BeginExp exp) {
        Expression head = exp.head;
        List<Expression> tail = exp.tail;
        
        if (tail.size() == 0) {
            return head.visit(this);
        } else {
            head.visit(this);
            List<Expression> cdrTail = new LinkedList<>();
            cdrTail.addAll(tail);
            cdrTail.remove(0);
            BeginExp rest = new BeginExp(tail.get(0), cdrTail);
            return rest.visit(this);
        }
    }

    @Override
    public RetType visit(CallExp exp) {
        ProcVal rator = (ProcVal)exp.rator.visit(this);
        List<Expression> rands = exp.rands;
        
        List<String> argsName = rator.args;
        List<ExpVal> args = new LinkedList<>();
        for (Expression rand : rands) {
            args.add((ExpVal)rand.visit(this));
        }
        
        Environment oldEnv = env;
        env = rator.savedEnv;
        for (int i = 0; i < args.size(); ++i) {
            env.extendEnv(argsName.get(i), args.get(i));
        }
        
        RetType retVal = rator.body.visit(this);
        
        env = oldEnv;
        return retVal;
    }

    @Override
    public RetType visit(CarExp exp) {
        ListVal ls = (ListVal)exp.exp1.visit(this);
        
        return ls.head;
    }

    @Override
    public RetType visit(CdrExp exp) {
        ListVal ls = (ListVal)exp.exp1.visit(this);
        
        return ls.tail;
    }

    @Override
    public RetType visit(CondExp exp) {
        List<Expression> conds = exp.condExps;
        List<Expression> thens = exp.thenExps;
        
        for (int i = 0; i < conds.size(); ++i) {
            boolean guard = ((BoolVal)conds.get(i).visit(this)).val;
            if (guard) {
                return thens.get(i).visit(this);
            }
        }
        
        return null;
    }

    @Override
    public RetType visit(ConstExp exp) {
        return new NumVal(exp.number);
    }

    @Override
    public RetType visit(ConsExp exp) {
        
        ExpVal head = (ExpVal)exp.exp1.visit(this);
        ExpVal tail = (ExpVal)exp.exp2.visit(this);
        
        return new ListVal(head, tail);
    }

    @Override
    public RetType visit(DerefExp exp) {
        RefVal ref = (RefVal)exp.exp1.visit(this);
        
        return store.getLocation(ref.loc);
    }

    @Override
    public RetType visit(DiffExp exp) {
        return visitBinaryExp(exp, LETTokenType.MINUS);
    }

    @Override
    public RetType visit(EmptylistExp exp) {
        return new ListVal(null, null);
    }

    @Override
    public RetType visit(EqualQuestionExp exp) {
        return visitBinaryExp(exp, LETTokenType.EQUAL_QUESTION);
    }

    @Override
    public RetType visit(GreaterQuestionExp exp) {
        return visitBinaryExp(exp, LETTokenType.GREATER_QUESTION);
    }

    @Override
    public RetType visit(IfExp exp) {
        boolean guard = ((BoolVal)exp.guard.visit(this)).val;
        if (guard) {
            return exp.thenbr.visit(this);
        } else {
            return exp.elsebr.visit(this);
        }
    }

    @Override
    public RetType visit(LessQuestionExp exp) {
        return visitBinaryExp(exp, LETTokenType.LESS_QUESTION);
    }

    @Override
    public RetType visit(LetExp exp) {
        List<String> vars = exp.vars;
        List<Expression> exps = exp.exps;
        Expression body = exp.body;
        
        int len = exp.vars.size();
        
        Map<String, ExpVal> oldVals = new TreeMap<String, ExpVal>();
        Map<String, ExpVal> newVals = new TreeMap<String, ExpVal>();
        for (int i = 0; i < len; ++i) {
            String var = vars.get(i);
            if (env.containsVal(var)) {
                oldVals.put(var, env.findVal(var));
            }
            ExpVal val = (ExpVal)exps.get(i).visit(this);
            newVals.put(var, val);
        }
        
        for (String var : newVals.keySet()) {
            env.extendEnv(var, newVals.get(var));
        }
        
        RetType retVal = body.visit(this);
        
        for (int i = 0; i < len; ++i) {
            String var = vars.get(i);
            env.removeVar(var);
        }
        
        for (String oldVar : oldVals.keySet()) {
            env.extendEnv(oldVar, oldVals.get(oldVar));
        }
        
        return retVal;
    }

    @Override
    public RetType visit(LetmutableExp exp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RetType visit(LetrecExp exp) {

        List<String> fnames = exp.fnames;
        List<List<String>> fargs = exp.fargs;
        List<Expression> fbodies = exp.fbodies;
        Expression lBody = exp.letrecBody;
        
        Map<String, ExpVal> procLevel = new TreeMap<String, ExpVal>();
        int len = fnames.size(); 
        for (int i = 0; i < len; ++i) {
            procLevel.put(fnames.get(i), 
                    new ProcVal(fargs.get(i), fbodies.get(i), new Environment(env)));
        }
       
        Environment oldEnv = new Environment(env);
        env.extendEnvRec(procLevel);
        RetType retVal = lBody.visit(this);
        env = oldEnv; 
        
        return retVal;
    }

    @Override
    public RetType visit(LetStarExp exp) {
        List<String> vars = exp.vars;
        List<Expression> exps = exp.exps;
        Expression body = exp.body;
        
        int len = exp.vars.size();
        
        Map<String, ExpVal> oldVals = new TreeMap<String, ExpVal>();
        for (int i = 0; i < len; ++i) {
            String var = vars.get(i);
            if (env.containsVal(var)) {
                oldVals.put(var, env.findVal(var));
            }
            ExpVal val = (ExpVal)exps.get(i).visit(this);
            env.extendEnv(var, val);
        }
        
        RetType retVal = body.visit(this);
        
        for (int i = 0; i < len; ++i) {
            String var = vars.get(i);
            env.removeVar(var);
        }
        
        for (String oldVar : oldVals.keySet()) {
            env.extendEnv(oldVar, oldVals.get(oldVar));
        }
        
        return retVal;
    }

    @Override
    public RetType visit(ListExp exp) {
        
        List<Expression> exps = exp.exps;
        if (exps.size() == 0) {
            return new ListVal(null, null);
        } else {
            ExpVal head = (ExpVal)exps.get(0).visit(this);
            List<Expression> rest = new LinkedList<>();
            rest.addAll(exps);
            rest.remove(0);
            ListExp tailExp = new ListExp(rest);
            ExpVal tail = (ExpVal)tailExp.visit(this);
            return new ListVal(head, tail);
        }
    }

    @Override
    public RetType visit(MulExp exp) {
        return visitBinaryExp(exp, LETTokenType.MUL);
    }

    @Override
    public RetType visit(NegateExp exp) {
        return visitUnaryExp(exp, LETTokenType.NEGATE);
    }

    @Override
    public RetType visit(NewrefExp exp) {
        ExpVal val = (ExpVal)exp.exp1.visit(this);
        store.insertLocation(locCounter, val);
        ExpVal retVal = new RefVal(locCounter);
        locCounter++;
        
        return retVal;
    }

    @Override
    public RetType visit(NullQuestionExp exp) {
        ListVal ls = (ListVal)exp.exp1.visit(this);
        
        return new BoolVal(ls.isNull());
    }

    @Override
    public RetType visit(ProcExp exp) {
        return new ProcVal(exp.vars, exp.body, new Environment(env));
    }

    @Override
    public RetType visit(QuotientExp exp) {
        return visitBinaryExp(exp, LETTokenType.QUOTIENT);
    }

    @Override
    public RetType visit(SetdynamicExp exp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RetType visit(SetrefExp exp) {
        
        int loc = ((RefVal)exp.exp1.visit(this)).loc;
        ExpVal toSet = (ExpVal)exp.exp2.visit(this);
        store.modifyLocation(loc, toSet);
        
        return new NumVal(23);
    }

    @Override
    public RetType visit(TraceprocExp exp) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RetType visit(UnpackExp exp) {
        
        List<String> vars = exp.vars;
        List<ExpVal> vals = ((ListVal)exp.exp1.visit(this)).allVals();
        
        Map<String, ExpVal> oldVals = new TreeMap<String, ExpVal>();
        for (int i = 0; i < vars.size(); ++i) {
            String var = vars.get(i);
            if (env.containsVal(var)) {
                oldVals.put(var, env.findVal(var));
            }
            env.extendEnv(var, vals.get(i));
        }
        
        RetType retVal = exp.body.visit(this);
        
        for (int i = 0; i < vars.size(); ++i) {
            env.removeVar(vars.get(i));
        }
        for (String var : oldVals.keySet()) {
            env.extendEnv(var, oldVals.get(var));
        }
        
        return retVal;
    }

    @Override
    public RetType visit(VarExp exp) {
        String var = exp.var;
        
        if (env.containsProc(var)) {
            ProcVal savedProc = (ProcVal)env.findProc(var);
            return new ProcVal(savedProc.args, savedProc.body, env);
        } else if (env.containsVal(var)) {
            return env.findVal(var);
        } else {
            for (String mName : moduleEnv.keySet()) {
                Map<String, ExpVal> mEnv = moduleEnv.get(mName);
                if (mEnv.containsKey(var)) {
                    return mEnv.get(var);
                }
            }
            return null;
        }
    }

    @Override
    public RetType visit(ZeroQuestionExp exp) {
        return visitUnaryExp(exp, LETTokenType.ZERO_QUESTION);
    }

    @Override
    public RetType visit(QualifiedVarExp exp) {
        String mName = exp.fromVar;
        String var = exp.takeVar;
        
        return moduleEnv.get(mName).get(var);
    }

    @Override
    public RetType visit(NewpairExp exp) {
        ExpVal lval = (ExpVal)exp.exp1.visit(this);
        ExpVal rval = (ExpVal)exp.exp2.visit(this);
        
        return new PairVal(lval, rval);
    }

    @Override
    public RetType visit(UnpairExp exp) {
        String left = exp.left;
        String right = exp.right;
        PairVal pVal = (PairVal)exp.pairExp.visit(this);
        
        Environment oldEnv = this.env;
        this.env = new Environment(this.env);
        env.extendEnv(left, pVal.left);
        env.extendEnv(right, pVal.right);
        
        RetType retVal = exp.body.visit(this);
        
        this.env = oldEnv;
        
        return retVal;
    }
}
