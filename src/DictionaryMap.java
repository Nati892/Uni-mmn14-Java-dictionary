import java.io.Serializable;
import java.util.*;


public class DictionaryMap implements Serializable {

    private static final String TAG = "DictionaryMap";//TODO delete line
    private TreeMap<String, DictionaryEntry> map;

    public DictionaryMap() {
        this.map = new TreeMap<String, DictionaryEntry>(new MyTreeComperator());
    }

    public DictionaryEntry getEntry(int i) {
        System.out.println(TAG + ": get Entry(int)" + " started");
        if (i > 0 && i < map.size()) return map.get(i);
        return null;
    }

    public DictionaryEntry getEntry(String key) {
        System.out.println(TAG + ": get Entry(String)" + " started");
        if (map.containsKey(key)) return map.get(key);
        return null;
    }


    public void insert(DictionaryEntry newEntry) {
        System.out.println(TAG + ": insert" + " started");
        System.out.println("map looks like:" + this);
        System.out.println("found : " + this.find(newEntry.getKey()));
        if (newEntry != null && this.find(newEntry.getKey()) == null) {
            map.put(newEntry.getKey(), newEntry);
        }
    }


    public Set<Map.Entry<String, DictionaryEntry>> entrySet() {
        System.out.println(TAG + ": entry set" + " started");
        return map.entrySet();
    }

    public DictionaryEntry find(String key) {
        System.out.println(TAG + ": find" + " started with key->" + key);
        return map.get(key);
    }

    public void remove(String key) {
        System.out.println(TAG + ": remove" + " started");
        map.remove(key);
    }

    @Override
    public String toString() {
        return "DictionaryMap{" + "_data=" + map + '}';
    }
}
