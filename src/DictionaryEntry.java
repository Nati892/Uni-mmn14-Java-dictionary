import java.io.Serializable;

public class DictionaryEntry implements Comparable<DictionaryEntry> , Serializable {
    private String key;
    private String value;


    public DictionaryEntry(String key, String value) {
        if (key == null)
            key = "";
        else
            this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String _key) {
        this.key = _key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String _value) {
        this.value = _value;
    }

    @Override
    public String toString() {
        return "DictionaryEntry{" + "Key='" + key + '\'' + ", Value='" + value + '\'' + '}';
    }

    /**
     * compares in lexicographic order
     */
    @Override
    public int compareTo(DictionaryEntry o) {
        return this.getKey().compareTo(o.getKey());
    }
}
