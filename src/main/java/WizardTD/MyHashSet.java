package WizardTD;
import java.util.*;

public class MyHashSet {
    
    Map<ArrayList<Integer>, Boolean> hashMap = new HashMap<ArrayList<Integer>, Boolean>();

    public void add(ArrayList<Integer> key) {
        hashMap.put(key, true);
    }

    public boolean contains(ArrayList<Integer> key) {
        return hashMap.containsKey(key);
    }
}
