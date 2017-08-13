package letlang;

import java.util.LinkedList;
import java.util.List;

import general.*;
import letlang.ast.*;
import letlang.module.*;
import letlang.type.*;

public class LETParser extends Parser<LETTokenType> {

    public LETParser(Lexer<LETTokenType> lexer) throws Exception {
        super(lexer);
    }
    
    public Expression parseExp() throws Exception {
        
        LETTokenType type = lookahead.type;
        System.out.println(lookahead.text +" " + type);
        switch (type) {
        case NUMBER: 
            return matchConstExp(); 
        case MINUS: 
            return matchBinaryExp(LETTokenType.MINUS);
        case ZERO_QUESTION: 
            return matchUnaryExp(LETTokenType.ZERO_QUESTION);
        case IF:
            return matchIfExp();
        case IDENTIFIER:
            return matchVarExp();
        case LET:
            return matchLetExp(LETTokenType.LET);
        case LET_STAR:
            return matchLetExp(LETTokenType.LET_STAR);
        case NEGATE:
            return matchUnaryExp(LETTokenType.NEGATE);
        case ADD:
            return matchBinaryExp(LETTokenType.ADD);
        case MUL:
            return matchBinaryExp(LETTokenType.MUL);
        case QUOTIENT:
            return matchBinaryExp(LETTokenType.QUOTIENT);
        case EQUAL_QUESTION:
            return matchBinaryExp(LETTokenType.EQUAL_QUESTION);
        case GREATER_QUESTION:
            return matchBinaryExp(LETTokenType.GREATER_QUESTION);
        case LESS_QUESTION:
            return matchBinaryExp(LETTokenType.LESS_QUESTION);
        case CONS:
            return matchBinaryExp(LETTokenType.CONS);
        case CAR:
            return matchUnaryExp(LETTokenType.CAR);
        case CDR:
            return matchUnaryExp(LETTokenType.CDR);
        case NULL_QUESTION:
            return matchUnaryExp(LETTokenType.NULL_QUESTION);
        case EMPTYLIST:
            return matchNullArgExp(LETTokenType.EMPTYLIST);
        case LIST:
            return matchListExp();
        case COND:
            return matchCondExp();
        case UNPACK:
            return matchUnpackExp();
        case PROC:
            return matchProcExp(LETTokenType.PROC);
        case LPAREN:
            return matchCallExp();
        case LETREC:
            return matchLetrecExp();
        case NEWREF:
            return matchUnaryExp(LETTokenType.NEWREF);
        case DEREF:
            return matchUnaryExp(LETTokenType.DEREF);
        case SETREF:
            return matchBinaryExp(LETTokenType.SETREF);
        case SET:
            return matchAssignExp();
        case LETMUTABLE:
            return matchTernaryExp(LETTokenType.LETMUTABLE);
        case SETDYNAMIC:
            return matchTernaryExp(LETTokenType.SETDYNAMIC);
        case BEGIN:
            return matchBeginExp();
        case FROM:
            return parseQualifiedVarExp();
        case NEWPAIR:
            return matchBinaryExp(LETTokenType.NEWPAIR);
        case UNPAIR:
            return matchUnpairExp();
        default:
                throw new Exception("incorrect syntax");
        }
    }
    
    
    public void match(LETTokenType x) throws Exception {
        
        if (lookahead.type.equals(x)) {
            consume();
        } else {
            throw new Exception("inconsistent");
        }
    }
    
    private Expression matchConstExp() throws Exception {
        // Number
        int num = Integer.parseInt(lookahead.text);
        match(LETTokenType.NUMBER);
        
        return new ConstExp(num);
    }
    
    
    private Expression matchIfExp() throws Exception {
        // if Expression then Expression else Expression
        match(LETTokenType.IF);
        Expression guard = parseExp();
        match(LETTokenType.THEN);
        Expression thenbr = parseExp();
        match(LETTokenType.ELSE);
        Expression elsebr = parseExp();
        
        return new IfExp(guard, thenbr, elsebr);
    }
    
    private Expression matchVarExp() throws Exception {
        // Identifier
        String var = lookahead.text;
        match(LETTokenType.IDENTIFIER);
        
        return new VarExp(var);
    }
    
    private Expression matchLetExp(LETTokenType tlet) throws Exception {
        // let {Identifier = Expression}* in Expression
        List<String> vars = new LinkedList<>();
        List<Expression> exps = new LinkedList<>();
        
        match(tlet);
        
        while (lookahead.type.equals(LETTokenType.IDENTIFIER)) {
            vars.add(lookahead.text);
            match(LETTokenType.IDENTIFIER);
            match(LETTokenType.EQUAL);
            exps.add(parseExp());
        }
        
        match(LETTokenType.IN);
        Expression body = parseExp();
        
        switch (tlet) {
        case LET:
            return new LetExp(vars, exps, body);
        case LET_STAR:
            return new LetStarExp(vars, exps, body);
        default:
            throw new Exception();
        }
    }
    
    private Expression matchNullArgExp(LETTokenType opr) throws Exception {
        
        match(opr);
        
        switch (opr) {
        case EMPTYLIST:
            match(LETTokenType.UNDERSCORE);
            OptionalType ty = parseType();
            return new EmptylistExp(ty);
        default:
            throw new Exception();
        }
        
    }
    
    private Expression matchUnaryExp(LETTokenType opr) throws Exception {
        // op(Expression)
        match(opr);
        match(LETTokenType.LPAREN);
        Expression exp1 = parseExp();
        match(LETTokenType.RPAREN);
        
        switch (opr) {
        case ZERO_QUESTION:
            return new ZeroQuestionExp(exp1);
        case CAR:
            return new CarExp(exp1);
        case NEGATE:
            return new NegateExp(exp1);
        case CDR:
            return new CdrExp(exp1);
        case NULL_QUESTION:
            return new NullQuestionExp(exp1);
        case NEWREF:
            return new NewrefExp(exp1);
        case DEREF:
            return new DerefExp(exp1);
        default:
            throw new Exception();
        }
    }
    
    private Expression matchBinaryExp(LETTokenType opr) throws Exception {
        // op(Expression, Expression)
        match(opr);
        match(LETTokenType.LPAREN);
        Expression exp1 = parseExp();
        match(LETTokenType.COMMA);
        Expression exp2 = parseExp();
        match(LETTokenType.RPAREN);
        
        switch (opr) {
        case MINUS:
            return new DiffExp(exp1, exp2);
        case ADD:
            return new AddExp(exp1, exp2);
        case MUL:
            return new MulExp(exp1, exp2);
        case QUOTIENT:
            return new QuotientExp(exp1, exp2);
        case EQUAL_QUESTION:
            return new EqualQuestionExp(exp1, exp2);
        case LESS_QUESTION:
            return new LessQuestionExp(exp1, exp2);
        case GREATER_QUESTION:
            return new GreaterQuestionExp(exp1, exp2);
        case CONS:
            return new ConsExp(exp1, exp2);
        case SETREF:
            return new SetrefExp(exp1, exp2);
        case NEWPAIR:
            return new NewpairExp(exp1, exp2);
        default:
            throw new Exception();
        }
    }
    
    private Expression matchListExp() throws Exception {
        // list(Expression, ..., Expression)
        List<Expression> exps = new LinkedList<>();
        
        match(LETTokenType.LIST);
        match(LETTokenType.LPAREN);
        exps.add(parseExp());
        
        while (lookahead.type.equals(LETTokenType.COMMA)) {
            consume();
            exps.add(parseExp());
        }
        
        match(LETTokenType.RPAREN);
        
        return new ListExp(exps);
    }
    
    private Expression matchCondExp() throws Exception {
        // cond {Expression ==> Expression}* end
        List<Expression> condExps = new LinkedList<>();
        List<Expression> thenExps = new LinkedList<>();
        
        match(LETTokenType.COND);
        
        while (!lookahead.type.equals(LETTokenType.END)) {
            condExps.add(parseExp());
            match(LETTokenType.IF_COND);
            thenExps.add(parseExp());
        }
        
        match(LETTokenType.END);
        
        return new CondExp(condExps, thenExps);
    }
    
    private Expression matchUnpackExp() throws Exception {
        // unpack {identifier}* = Expression in Expression
        List<String> vars = new LinkedList<>();
        
        match(LETTokenType.UNPACK);
        
        while (lookahead.type.equals(LETTokenType.IDENTIFIER)) {
            vars.add(lookahead.text);
            consume();
        }
        
        match(LETTokenType.EQUAL);
        Expression exp1 = parseExp();
        match(LETTokenType.IN);
        Expression body = parseExp();
        
        return new UnpackExp(vars, exp1, body);
    }
    
    private Expression matchProcExp(LETTokenType tproc) throws Exception {
        // proc (identifier : Type)* expression
        List<String> args = new LinkedList<>();
        List<OptionalType> argTypes = new LinkedList<>();
        
        match(tproc);
        match(LETTokenType.LPAREN);
        args.add(lookahead.text); consume();
        match(LETTokenType.COLON);
        argTypes.add(parseType());
        while (lookahead.type.equals(LETTokenType.COMMA)) {
            args.add(lookahead.text);
            consume();
            match(LETTokenType.COLON);
            argTypes.add(parseType());
        }
        
        match(LETTokenType.RPAREN);
        Expression body = parseExp();
        
        return new ProcExp(args, argTypes, body);
    }
    
    private Expression matchCallExp() throws Exception {
        // (Expression Expression ... Expression)
        List<Expression> rands = new LinkedList<>();
        match(LETTokenType.LPAREN);
        Expression rator = parseExp();
        
        while (!lookahead.type.equals(LETTokenType.RPAREN)) {
            rands.add(parseExp());
        }
        
        match(LETTokenType.RPAREN);
        
        return new CallExp(rator, rands);
    }
    
    private Expression matchLetrecExp() throws Exception {
        // letrec 
        // retType f1( {identifier : Type}* ) = body
        // retType f2( {identifier : Type}* ) = body
        // ...
        // in letrec-body
        List<String> fnames = new LinkedList<>();
        List<OptionalType> retTypes = new LinkedList<>();
        List<List<String>> fargs = new LinkedList<>();
        List<List<OptionalType>> fargTypes = new LinkedList<>();
        List<Expression> fbodies = new LinkedList<>();
        
        match(LETTokenType.LETREC);
        
        while (!lookahead.type.equals(LETTokenType.IN)) {
            // function ret type
            retTypes.add(parseType());
            // function names
            String fname = lookahead.text;
            fnames.add(fname);
            consume();
            match(LETTokenType.LPAREN);
            // arguments
            List<String> args = new LinkedList<>();
            List<OptionalType> argTypes = new LinkedList<>();
            args.add(lookahead.text); consume();
            match(LETTokenType.COLON);
            argTypes.add(parseType());
            
            while (lookahead.type.equals(LETTokenType.COMMA)) {
                consume();
                // arg name
                args.add(lookahead.text);
                consume();
                // arg type
                match(LETTokenType.COLON);
                argTypes.add(parseType());
            }
            fargs.add(args);
            fargTypes.add(argTypes);
            match(LETTokenType.RPAREN);
            match(LETTokenType.EQUAL);
            // function bodies
            Expression fbody = parseExp();
            fbodies.add(fbody);
        }
        
        match(LETTokenType.IN);
        Expression letrecBody = parseExp();
        
        return new LetrecExp(fnames, retTypes, fargs, fargTypes, fbodies, letrecBody);
    }
    
    private Expression matchAssignExp() throws Exception {
        // set Identifier = Expression
        match(LETTokenType.SET);
        String var = lookahead.text;
        match(LETTokenType.IDENTIFIER);
        match(LETTokenType.EQUAL);
        Expression exp = parseExp();
        
        return new AssignExp(var, exp);
    }
    
    private Expression matchTernaryExp(LETTokenType opr1) throws Exception {
        // opr1 Identifier = Expression opr2 Expression
        LETTokenType opr2;
        switch (opr1) {
        case LETMUTABLE:
            opr2 = LETTokenType.IN;
        case SETDYNAMIC:
            opr2 = LETTokenType.DURING;
        default:
            opr2 = null;
        }
        
        match(opr1);
        String var = lookahead.text;
        match(LETTokenType.IDENTIFIER);
        match(LETTokenType.EQUAL);
        Expression exp1 = parseExp();
        match(opr2);
        Expression exp2 = parseExp();
        
        
        switch (opr1) {
        case LETMUTABLE:
            return new LetmutableExp(var, exp1, exp2);
        case SETDYNAMIC:
            return new SetdynamicExp(var, exp1, exp2);
        default:
            throw new Exception();
        }
    }
    
    private Expression matchBeginExp() throws Exception {
        // begin Expression {; Expression}* end
        List<Expression> tail = new LinkedList<>();
        
        match(LETTokenType.BEGIN);
        Expression head = parseExp();
        
        while (lookahead.type.equals(LETTokenType.SEMICOLON)) {
            consume();
            tail.add(parseExp());
        }
        
        match(LETTokenType.END);
        
        return new BeginExp(head, tail);
    }
    
    private OptionalType parseType() throws Exception {
        LETTokenType token = lookahead.type;
        
        System.out.println(token);
        switch (token) {
        case INT:
            consume();
            return new IntType();
        case BOOL:
            consume();
            return new BoolType();
        case PAIROF:
            return parsePairType();
        case OPTION:
            consume();
            return new NoType();
        case LISTOF:
            return parseListType();
        case IDENTIFIER:
            String tname = lookahead.text; consume();
            return new NamedType(tname);
        case FROM:
            return parseQualifiedType();
        default:
            return parseProcType();
        }
    }
    
    private OptionalType parseQualifiedType() throws Exception {
        match(LETTokenType.FROM);
        String mName = lookahead.text; consume();
        match(LETTokenType.TAKE);
        String tName = lookahead.text; consume();
        
        return new QualifiedType(mName, tName);
    }
    
    private OptionalType parsePairType() throws Exception {
        match(LETTokenType.PAIROF);
        OptionalType ty1 = parseType();
        match(LETTokenType.PRODUCT);
        OptionalType ty2 = parseType();
        
        return new PairType(ty1, ty2);
    }
    
    private OptionalType parseProcType() throws Exception {
        // (Type -> Type)
        List<OptionalType> argTypes = new LinkedList<>();
        match(LETTokenType.LPAREN);
        argTypes.add(parseType());
        while (lookahead.type.equals(LETTokenType.PRODUCT)) {
            consume();
            argTypes.add(parseType());
        }
        match(LETTokenType.DERIVE);
        OptionalType resType = parseType();
        match(LETTokenType.RPAREN);
        
        return new ProcType(argTypes, resType);
    }
    
    private VarDecl parseVarDecl(LETTokenType tok) throws Exception {
        // opaque Identifier
        // transparent Identifier = Type
        // Identifier : type
        String var = null;
        switch (tok) {
        case OPAQUE:
            match(tok);
            var = lookahead.text; consume();
            return new OpaqueVarDecl(var);
        case TRANSPARENT:
            match(tok);
            var = lookahead.text; consume();
            match(LETTokenType.EQUAL);
            OptionalType transType = parseType();
            return new TransparentVarDecl(var, transType);
        default:
            var = lookahead.text; consume();
            match(LETTokenType.COLON);
            OptionalType ntype = parseType();
            return new NormalVarDecl(var, ntype);
        }
    }
    
    private VarDefn parseVarDefn() throws Exception {
        // Identifier = Expression
        // type Identifier = Type
        String var = null;
        LETTokenType tok = lookahead.type;
        System.out.println(tok);
        switch (tok) {
        case TYPE:
            match(tok);
            var = lookahead.text; consume();
            match(LETTokenType.EQUAL);
            OptionalType type = parseType();
            
            return new TypeVarDefn(var, type);
        default:
            var = lookahead.text; consume();
            match(LETTokenType.EQUAL);
            Expression exp = parseExp();
            
            return new NormalVarDefn(var, exp);
        }

    }
    
    private Iface parseNormalIface() throws Exception {
     // [ {Decl}* ]
        List<VarDecl> decls = new LinkedList<>();
        
        match(LETTokenType.LBRACK);
        while (!lookahead.type.equals(LETTokenType.RBRACK)) {
            decls.add(parseVarDecl(lookahead.type));
        }
        match(LETTokenType.RBRACK);
        
        return new NormalIface(decls);
    }
    
    private Iface parseProcIface() throws Exception {
        // ((Identifier : Iface) => Iface)
        match(LETTokenType.LPAREN);
        match(LETTokenType.LPAREN);
        String pname = lookahead.text; consume();
        match(LETTokenType.COLON);
        Iface piface = parseIface();
        match(LETTokenType.RPAREN);
        match(LETTokenType.INTERFACEDERIVE);
        Iface riface = parseIface();
        match(LETTokenType.RPAREN);
        
        return new ProcIface(pname, piface, riface);
    }
    
    private Iface parseIface() throws Exception {
        
        switch (lookahead.type) {
        case LPAREN:
            return parseProcIface();
        default:
            return parseNormalIface();
        }
        
    }
    
    private ModuleBody parseNormalModuleBody() throws Exception {
        System.out.println("parseNormalModuleBody");
        // [ {Defn}* ]
        List<VarDefn> defns = new LinkedList<>();
        match(LETTokenType.LBRACK);
        while (!lookahead.type.equals(LETTokenType.RBRACK)) {
            defns.add(parseVarDefn());
        }
        match(LETTokenType.RBRACK);
        
        return new NormalModuleBody(defns);
    }
    
    private ModuleBody parseProcModuleBody() throws Exception {
        System.out.println("parseProcModuleBody");
        // module-proc (Identifier : Iface) ModuleBody
        match(LETTokenType.MODULE_PROC);
        match(LETTokenType.LPAREN);
        String mName = lookahead.text; consume();
        match(LETTokenType.COLON);
        Iface mType = parseIface();
        match(LETTokenType.RPAREN);
        ModuleBody mBody = parseModuleBody();
        
        return new ProcModuleBody(mName, mType, mBody);
    }
    
    private ModuleBody parseVarModuleBody() throws Exception {
        System.out.println("parseVarModuleBody");
            String var = lookahead.text; consume();
            return new VarModuleBody(var);
    }
    
    private ModuleBody parseAppModuleBody() throws Exception {
        System.out.println("parseAppModuleBody");
        
        match(LETTokenType.LPAREN);
        String rator = lookahead.text; consume();
        String rand = lookahead.text; consume();
        match(LETTokenType.RPAREN);
        
        return new AppModuleBody(rator, rand);
    }
    
    private ModuleBody parseModuleBody() throws Exception {
        
        switch (lookahead.type) {
        case MODULE_PROC:
            return parseProcModuleBody();
        case IDENTIFIER:
            return parseVarModuleBody();
        case LPAREN:
            return parseAppModuleBody();
        default:
            return parseNormalModuleBody();
        }
    }
    
    private Expression parseQualifiedVarExp() throws Exception {
        // from Identifier take Identifier
        match(LETTokenType.FROM);
        String fromVar = lookahead.text; consume();
        match(LETTokenType.TAKE);
        String takeVar = lookahead.text; consume();
        
        return new QualifiedVarExp(fromVar, takeVar);
    }
    
    private ModuleDefn parseModuleDefn() throws Exception {
        // module Identifier interface Iface body ModuleBody
        match(LETTokenType.MODULE);
        String var = lookahead.text; consume();
        match(LETTokenType.INTERFACE);
        Iface iface = parseIface();
        match(LETTokenType.BODY);
        ModuleBody mBody = parseModuleBody();
        
        return new ModuleDefn(var, iface, mBody);
    }
    
    private OptionalType parseListType() throws Exception {
        match(LETTokenType.LISTOF);
        OptionalType ty = parseType();
        return new ListType(ty);
    }
    
    private Expression matchUnpairExp() throws Exception {
        // unpair Identifier Identifier = Expression in Expression
        match(LETTokenType.UNPAIR);
        String left = lookahead.text; consume();
        String right = lookahead.text; consume();
        match(LETTokenType.EQUAL);
        Expression pairExp = parseExp();
        match(LETTokenType.IN);
        Expression body = parseExp();
        
        return new UnpairExp(left, right, pairExp, body);
    }
    
    
    public Program parseProg() throws Exception {
        // {ModuleDefn}* Expression
        List<ModuleDefn> mDefs = new LinkedList<>();
        while (lookahead.type.equals(LETTokenType.MODULE)) {
            mDefs.add(parseModuleDefn());
        }
        Expression exp = parseExp();
        
        return new Program(mDefs, exp);
    }
}
