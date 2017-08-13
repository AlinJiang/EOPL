package letlang.environment;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import letlang.expval.ExpVal;
import letlang.expval.NumVal;

public class Environment {

    public Map<String, ExpVal> env;
    public List<Map<String, ExpVal>> procs;
    
    public Environment() {
        env = new TreeMap<String, ExpVal>();
        env.put("i", new NumVal(1));
        env.put("x", new NumVal(5));
        env.put("v", new NumVal(10));
        
        procs = new LinkedList<>();
    }
    
    public Environment(Environment newEnv) {
        env = new TreeMap<String, ExpVal>();
        procs = new LinkedList<>();
        env.putAll(newEnv.env);
    }
    
    public void extendEnvRec(Map<String, ExpVal> declFuncs) {
        procs.add(declFuncs);
    }
    
    public boolean containsProc(String fname) {
        for (int i = 0; i < procs.size(); ++i) {
            Map<String, ExpVal> currLevel = procs.get(i);
            if (currLevel.containsKey(fname)) {
                return true;
            }
        }
        return false;
    }
    
    public void extendEnv(String var, ExpVal expVal) {
        env.put(var, expVal);
    }
    
    
    public void removeVar(String var) {
        env.remove(var);
    }
    
    public ExpVal findVal(String var) {
        return env.get(var);
    }
    
    public ExpVal findProc(String fname) {
        for (int i = 0; i < procs.size(); ++i) {
            Map<String, ExpVal> currLevel = procs.get(i);
            if (currLevel.containsKey(fname)) {
                return currLevel.get(fname);
            }
        }
        return null;
    }
    
    public boolean containsVal(String var) {
        return env.containsKey(var);
    }
    
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("env = \n");
        
        for (String var : env.keySet()) {
            buf.append(var + ": " + env.get(var));
            buf.append("\n");
        }
        
        return buf.toString();
    }
    
}
