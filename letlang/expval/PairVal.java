package letlang.expval;

public class PairVal extends ExpVal {

    public ExpVal left;
    public ExpVal right;
    
    public PairVal(ExpVal left, ExpVal right) {
        this.left = left;
        this.right = right;
    }
    
    public String toString() {
        return "(pair-exp " + left.toString() + " " + right.toString() + ")";
    }
    
}
