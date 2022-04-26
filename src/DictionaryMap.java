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
        _data.add(newEntry);
        Collections.sort(_data);

    }

    @Override
    public String toString() {
        return "DictionaryMap{" +
                "_data=" + _data +
                '}';
    }
}
