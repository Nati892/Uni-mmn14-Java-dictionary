import java.io.Serializable;
import java.util.*;


public class DictionaryMap implements Serializable {

    private TreeMap<String, DictionaryEntry> map;

    /**
     * empty constructor for the Dictionary map
     */
    //constructs the tree map with a comperator so that all pairs will be managed in order
    public DictionaryMap() {
        this.map = new TreeMap<String, DictionaryEntry>(new MyTreeComperator());
    }

    /**
     * get an entry by index
     * @param i the index of the entry
     * @return a DictionaryEntry object from the requested index, returns null if no such object was found
     */
    public DictionaryEntry getEntry(int i) {
        if (i > 0 && i < map.size()) return map.get(i);
        return null;
    }

    /**
     * get an entry by key value
     * @param key the key of the entry
     * @return a DictionaryEntry object with the requested key, returns null if no such object was found
     */
    public DictionaryEntry getEntry(String key) {
        if (map.containsKey(key)) return map.get(key);
        return null;
    }

    /**
     * inserts a new Dictionary entry, doesn't insert if there is entry with the same key already
     * @param newEntry the Dictionary entry to insert
     */
    public void insert(DictionaryEntry newEntry) {
        if (newEntry != null && this.getEntry(newEntry.getKey()) == null) {
            map.put(newEntry.getKey(), newEntry);
        }
    }

    /**
     *
     * @return an entry set of the Data structure
     */
    public Set<Map.Entry<String, DictionaryEntry>> entrySet() {
        return map.entrySet();
    }

    /**
     * removes a dictionary object from the dictionary map by key
     * @param key the key of object to remove
     */
    public void remove(String key) {
        map.remove(key);
    }


    @Override
    public String toString() {
        return "DictionaryMap{" + "_data=" + map + '}';
    }
}
