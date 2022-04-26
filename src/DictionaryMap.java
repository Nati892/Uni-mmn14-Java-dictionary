import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DictionaryMap {


    private ArrayList<DictionaryEntry> _data;

    public DictionaryMap() {
        this._data = new ArrayList<DictionaryEntry>();
    }

    public DictionaryEntry getEntry(int i) {
        if (i > 0 && i < _data.size())
            return _data.get(i);
        return null;
    }

    public Iterator<DictionaryEntry> iterator() {
        return _data.iterator();
    }

    public void insert(DictionaryEntry newEntry) {
        if (find(newEntry) < 0) {//avoid double keys
            _data.add(newEntry);
            Collections.sort(_data);
        }

    }

    //using linear search because time complexity is not important
    public int find(DictionaryEntry newEntry) {
        int index = -1;
        int result = -1;

        Iterator<DictionaryEntry> iterator = this._data.iterator();
        if (!iterator.hasNext() || newEntry.getKey() == null)
            return result;

        while (iterator.hasNext()) {
            index++;
            DictionaryEntry curr = iterator.next();
            if (curr.getKey().equals(newEntry.getKey()))
                result = index;
        }
        return result;
    }

    public void remove(DictionaryEntry entry) {
        if (entry != null) {
            _data.remove(entry);
        }

    }

    @Override
    public String toString() {
        return "DictionaryMap{" +
                "_data=" + _data +
                '}';
    }
}
