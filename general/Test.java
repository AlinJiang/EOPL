package general;

import letlang.LETLexer;
import letlang.LETParser;
import letlang.environment.Environment;
import letlang.interpreter.SemanticInterpreter;
import letlang.interpreter.TypeChecker;
import letlang.type.OptionalType;

public class Test {
    
    public static void main(String[] args) throws Exception {
        
        // initial env
        // [i=1, v=5, x=10]
        String[][] testCases = {

                {"module m1 interface [transparent t = int "
                        + "z:t s:(t->t) isz?:(t->bool)] "
                        + "body [type t = int "
                        + "z = 33 ss = proc(x:t) -(x,-1) "
                        + "isz? = proc(x:t) zero?(-(x,z))] "
                        + "proc(x:from m1 take t) (from m1 take isz? -(x,0))",""},
                
                {"module colors interface [opaque color "
                        + "red:color green:color isred?:(color->bool)] "
                        + "body [type color = int "
                        + "red = 0 green = 1 isred? = proc(c:color) zero?(c)] 1",""},
                
                {"module ints2 interface [opaque t "
                        + "zero:t succ:(t->t) pred:(t->t) iszero:(t->bool)] "
                        + "body [type t = int zero = 0 "
                        + "succ = proc(x:t) -(x,3) pred = proc(x:t) -(x,-3) "
                        + "iszero = proc(x:t) zero?(x)] "
                        + "let z = from ints2 take zero in let s = from ints2 take succ "
                        + "in (s (s z))",""},
                
                {"module tointmaker "
                        + "interface "
                        + "((ints:[opaque t "
                        + "zero:t succ:(t->t) pred:(t->t) iszero:(t->bool)]) => "
                        + "[toint:(from ints take t -> int)]) "
                        + "body moduleproc (ints:[opaque t "
                        + "zero:t succ:(t->t) pred:(t->t) iszero:(t->bool)]) "
                        + "[toint = let z = from ints take iszero in "
                        + "let p = from ints take pred in letrec int toint (x:from ints take t) = "
                        + "if (z x) then 0 else -((toint (p x)), -1) "
                        + "in toint] 3",""},
               
//                {"letrec int f(x:int) = if zero?(x) then 0 else (f -(x,1)) in "
//                        + "let x = (f 10) in "
//                        + "unpair x y = newpair(f, x) in x", "null"},
//                
//                {"proc (x:int) -(x,1)", "null"},
//               
//                {"letrec int double(x:int) = if zero?(x) then 0 else -((double -(x,1)), -2) in double", 
//                    "null"},
//                
//                {"proc(f:(bool->int)) proc(n:int) (f n)", "null"},
               
                
//                {"letrec ? foo(x:?) = if zero?(x) then 1 else -(x, (foo -(x,1))) in foo", 
//                    "null"},
                
//                {"cond less?(2, 1) ==> 1 greater?(2, 2) ==> 2  greater?(3, 2) ==> 3 end",
//                 "3"},
//                
//                {"cond less?(1, 2) ==> 1 greater?(2, 2) ==> 2  greater?(3, 2) ==> 3 end",
//                "1"},
//                
//                {"cond less?(2, 1) ==> 1 greater?(3, 2) ==> 2  greater?(3, 4) ==> 3 end",
//                "2"},
//                
//                {"if greater?(2, 1) then minus(1) else minus(2)", 
//                 "-1"},
//                
//                {"if equal?(2, 1) then minus(1) else minus(2)", 
//                "-2"},
//                
//                {"if equal?(2, 2) then minus(1) else minus(2)", 
//                "-1"},
//                
//                {"if less?(2, 1) then minus(1) else minus(2)", 
//                "-2"},
//                
//                {"let x = 10 in -(55,-(x, 11))", 
//                "56"},
//                
//                {"let x = 33 y = 22 in "
//                + "if zero?(-(x,11)) then -(y,2) else -(y,4)", 
//                
//                 "18"},
//                
//                {"let z = 5 in let x = 3 in let y = -(x,1) in let x = 4 "
//                        + "in -(z, -(x,y))", 
//                        
//                "3"},
//                
//                {"let x = 7 in let y = 2 in "
//                        + "let y = let x = -(x, 1) in -(x, y) "
//                        + "in -(-(x, 8), y)",
//                "-5"},
////                
//                {"let f = proc(x:int) -(x,1) in cons(f, emptylist_(int->int))", ""},
//                
//                {"let x = 4 in cons(x, cons(cons(-(x,1), emptylist_int), emptylist_listof int))",
//                "(4 (3))"},
//                
//                {"let x = 4 in list(x, -(x,1), -(x,3))", 
//                "(4 3 1)"},
//                
//                {"let x = 30 in let x = -(x, 1) y = -(x,2) in -(x,y)", 
//                "1"},
//                
//                {"let x = 30 in let* x = -(x, 1) y = -(x,2) in -(x,y)", 
//                "2"},
//                
//                {"let u = 7 in unpack x y = cons(u, cons(3, emptylist_int)) in -(x,y)", 
//                "4"},
//                
//                {"let f = proc(x:int) -(x, 11) in (f (f 77))", 
//                "55"},
//                
//                {"(proc(f:(int->int)) (f (f 77)) proc(x:int) -(x, 11))", 
//                "55"},
//                
//                {"let f = proc(x:int) proc(y:int) add(x, y) in ((f 3) 4)", 
//                    "7"},
//                
//                {"let x = 200 in let f = proc(z:int) -(z, x) "
//                        + "in let x = 100 in let g = proc(z:int) -(z, x)"
//                        + "in -((f 1), (g 1))", 
//                 "-100"},
//                
//                {"let makemult = proc(maker) proc(x)"
//                        + "if zero?(x) then 0 else -(((maker maker) -(x, 1)), -4)"
//                        + "in let times4 = proc(x) ((makemult makemult) x)"
//                        + "in (times4 3)", 
//                 "12"},
//                
//                {"let makerec = proc(f)"
//                        + "let d = proc(x) proc(z) ((f (x x)) z)"
//                        + "in proc(n) ((f (d d)) n)"
//                        + "in let maketimes4 = proc(f) proc(x)"
//                        + "if zero?(x) then 0 else -((f -(x,1)), -4)"
//                        + "in let times4 = (makerec maketimes4)"
//                        + "in (times4 3)", 
//                 "12"},
//                
//                {"letrec double(x) = "
//                        + "if zero?(x) then 0 else -((double -(x,1)), -2)"
//                        + "in (double 6)", 
//                 "12"},
//                
//                {"letrec "
//                        + "int even(x:int, y:bool) = if zero?(x) then 1 else (odd -(x,1)) "
//                        + "int odd(x:int) = if zero?(x) then 0 else (even -(x,1) 0) "
//                        + "in even", 
//                 "1"},
//                
//                {"let x = newref(0) in letrec "
//                        + "int even(dummy:int) = if zero?(deref(x)) "
//                        + "then 1 else begin "
//                        + "setref(x, -(deref(x), 1)); "
//                        + "(odd 888) "
//                        + "end "
//                        + "int odd(dummy:int) "
//                        + "= if zero?(deref(x)) "
//                        + "then 0 else begin "
//                        + "setref(x, -(deref(x), 1)); "
//                        + "(even 888) "
//                        + "end "
//                        + "in begin setref(x, 13); (odd 888) end", 
//                 "0"},
//                
//                {"let g = let counter = newref(0) "
//                        + "in proc(dummy:int) begin setref(counter, -(deref(counter), -1)); "
//                        + "deref(counter); "
//                        + "counter "
//                        + "end "
//                        + "in (g 1)", 
//                 "-1"},
//                
//                {"let g = proc(dummy) let counter = newref(0) in "
//                        + "begin setref(counter, -(deref(counter), -1)); "
//                        + "deref(counter) "
//                        + "end "
//                        + "in let a = (g 11) "
//                        + "in let b = (g 11) in -(a, b)", 
//                 "0"}, 
//                
//                {"let x = newref(newref(0)) in begin "
//                        + "setref(deref(x), 11); "
//                        + "deref(deref(x)) "
//                        + "end", 
//                  "11"},
//                  
//                {"let x = newref(22) in let f = proc(z) "
//                        + "let zz = newref(-(z, deref(x))) in deref(zz) "
//                        + "in -((f 66), (f 55))", 
//                 "11"},

                {"let x = 0 in letrec "
                        + "int even(dummy:int) = if zero?(x) then 1 else "
                        + "begin set x = -(x, 1); (odd 888) end "
                        + "int odd(dummy:int) = if zero?(x) then 0 else "
                        + "begin set x = -(x, 1); (even 888) end "
                        + "in begin set x = 13; (odd -888) end ", 
                 "1"},
                
                {"let g = let count = 0 in proc(dummy:int) begin "
                        + "set count = -(count, -1); count end "
                        + "in let a = (g 11) in let b = (g 11) in -(a,b)", 
                 "-1"},
                
        };
        
        for (String[] kvp : testCases) {
            String input = kvp[0];
            String ans = kvp[1];
            System.out.println("Program = " + input);
            LETLexer lexer = new LETLexer(input);
            LETParser parser = new LETParser(lexer);
            Program prog = parser.parseProg();
        
//            SemanticInterpreter semInterpret = new SemanticInterpreter();
//            System.out.println("result = " + prog.visit(semInterpret));
//           
//            TypeChecker typeChecker = new TypeChecker();
//            OptionalType resType = (OptionalType)prog.visit(typeChecker);
//            
//            System.out.println(resType);
            
            System.out.println(prog);
            System.out.println();
        }
        
    }
    
}