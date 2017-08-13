package letlang.expval;

import java.util.List;
import letlang.ast.Expression;
import letlang.environment.Environment;

public class ProcVal extends ExpVal {

    public List<String> args;
    public Expression body;
    public Environment savedEnv;
    
    public ProcVal(List<String> args, Expression body, Environment savedEnv) {
        this.args = args;
        this.body = body;
        this.savedEnv = savedEnv;
    }
    
    public String toString() {
        
        StringBuilder buf = new StringBuilder();
        buf.append(args.toString());
        buf.append("\n with body = ");
        buf.append(body.toString());
        buf.append("\n");
        //buf.append(savedEnv.toString());
        
        return buf.toString();
    }
    
}
