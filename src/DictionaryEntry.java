public class DictionaryEntry implements Comparable<DictionaryEntry> {
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
        return "DictioneryEntry{" + "Key='" + key + '\'' + ", Value='" + value + '\'' + '}';
    }

    @Override
    public int compareTo(DictionaryEntry o) {
        return compareLexicographic(this.key, o.getKey());
    }

    //this is a helper method that is basically the logic for the compareTo method
    //it compares the id of both members by a lexicographic order
    private int compareLexicographic(String first, String second) {
        int result = 0;
        if (first != null && second != null) {

            for (int i = 0; i < first.length() && i < second.length() && result == 0; i++) {
                if (first.charAt(i) > second.charAt(i)) {
                    result = 1;
                } else {
                    if (first.charAt(i) < second.charAt(i)) {
                        result = -1;
                    }
                }
            }
            if (result == 0) {
                if (first.length() > second.length()) {
                    result = 1;
                } else {
                    result = -1;
                }

            }
        } else {
            if (first == null && second == null) {
                result = 0;
            } else {
                {
                    if (first == null) return -1;//second is big
                    else return 1;//first is big
                }
            }
        }
        return result;
    }
}
