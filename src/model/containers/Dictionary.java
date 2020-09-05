package model.containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Dictionary<K,V> implements IDictionary<K,V> {
    private Map<K,V> dict = new HashMap<>();

    public Dictionary()
    {
        dict = new HashMap<>();
    }

    public Dictionary(Map<K, V> dictParameter)
    {
        dict = dictParameter;
    }

    @Override
    public void add(K key, V value) {
        this.dict.put(key, value);
    }

    @Override
    public void update(K key, V value) {
        this.dict.put(key, value);
    }

    @Override
    public boolean contains(K key) {
        return this.dict.containsKey(key);
    }

    @Override
    public V get(K key) {
        return this.dict.get(key);
    }

    @Override
    public Iterable<K> getAll()
    {
        return dict.keySet();
    }

    @Override
    public Collection<V> getAllValues()
    {
        return dict.values();
    }

    @Override
    public IDictionary<K, V> deepCopy() {
        //Copy Constructor of HashMap creates a new object with the same mappings
        return new Dictionary<K, V>(new HashMap<K, V>(dict));
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        if (this.dict.isEmpty()) {
            buff.append("Dictionary: empty\n");
        } else {
            buff.append("Dictionary: \n");
            for (Map.Entry<K, V> kvEntry : this.dict.entrySet()) {
                buff.append(kvEntry.getKey());
                buff.append(" = ");
                buff.append(kvEntry.getValue());
                buff.append("\n");
            }
        }
        return buff.toString();
    }
}
