package letlang.expval;

import java.util.LinkedList;
import java.util.List;

public class ListVal extends ExpVal {

    public ExpVal head;
    public ExpVal tail;
    
    public ListVal(ExpVal head, ExpVal tail) {
        this.head = head;
        this.tail = tail;
    }
    
    public boolean isNull() {
        return head == null && tail == null;
    }
    
    public List<ExpVal> allVals() {
        List<ExpVal> values = new LinkedList<>();

        if (!isNull()) {
            values.add(head);
            List<ExpVal> rest = ((ListVal)tail).allVals();
            values.addAll(rest);
        }
        return values;
    }
    
    public String toString() {
        if (!isNull()) {
            StringBuilder buf = new StringBuilder();
//            buf.append("(");
//            buf.append(head.toString());
//            buf.append(" ");
//            buf.append(tail.toString());
//            buf.append(")");
            
            for (ExpVal val : allVals()) {
                buf.append(val.toString() + ", ");
            }
    
            return buf.toString();
        } else {
            return "()";
        }
    }
    
}
