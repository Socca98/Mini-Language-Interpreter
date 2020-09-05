package model.files;

import java.util.HashMap;
import java.util.Map;

public class FileTable<K, V> implements IFileTable<K, V>{
    private Map<K, V> dict;

    public FileTable() {
        dict = new HashMap<>();
    }

    @Override
    public void add(K key, V value)
    {
        dict.put(key, value);
    }

    @Override
    public void remove(K key)
    {
        dict.remove(key);
    }

    @Override
    public boolean contains(K key)
    {
        return dict.containsKey(key);
    }

    @Override
    public V get(K key)
    {
        return dict.get(key);
    }

    @Override
    public Iterable<K> getAll()
    {
        return dict.keySet();
    }

    @Override
    public Iterable<V> getValues() {
        return dict.values();
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        if(dict.isEmpty()) {
            buff.append("FileTable: empty\n");
        }
        else {
            buff.append("FileTable: \n");

            for(Map.Entry<K,V> entry : dict.entrySet()) {
                buff.append(entry.getKey());
                buff.append(" --> (");
                buff.append(entry.getValue()).append(")");
                buff.append("\n");
            }
        }
        return buff.toString();
    }
}
