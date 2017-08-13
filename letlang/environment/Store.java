package letlang.environment;

import java.util.HashMap;
import java.util.Map;
import letlang.expval.ExpVal;

public class Store {

    public Map<Integer, ExpVal> locationHash;
    
    public Store() {
        locationHash = new HashMap<Integer, ExpVal>();
    }
    
    public void insertLocation(Integer loc, ExpVal val) {
        locationHash.put(loc, val);
    }
    
    public void modifyLocation(Integer loc, ExpVal val) {
        locationHash.put(loc, val);
    }
    
    public ExpVal getLocation(Integer loc) {
        return locationHash.get(loc);
    }
    
    public String toString() {
        return locationHash.toString();
    }
    
}
