package letlang.expval;

public class BoolVal extends ExpVal {

    public boolean val;
    
    public BoolVal(boolean val) {
        this.val = val;
    }
    
    public String toString() {
        //return "(bool-val " + val + ")";
        return "" + val;
    }
    
    
}
