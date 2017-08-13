package letlang.expval;

public class NumVal extends ExpVal {

    public int number;
    
    public NumVal(int number) {
        this.number = number;
    }
    
    public String toString() {
        //return "(num-val " + number + ")";
        return "" + number;
    }
    
}
