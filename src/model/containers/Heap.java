package model.containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap<K, V> implements IHeap<K, V> {
    private Map<K, V> heap = new HashMap<>();

    @Override
    public void add(K key, V value)
    {
        heap.put(key, value);
    }

    @Override
    public void update(K key, V value)
    {
        heap.put(key, value);
    }

    @Override
    public boolean contains(K key)
    {
        return heap.containsKey(key);
    }

    @Override
    public V get(K key)
    {
        return heap.get(key);
    }

    @Override
    public Iterable<K> getKeySet()
    {
        return heap.keySet();
    }

    @Override
    public Collection<V> getValueSet()
    {
        return heap.values();
    }

    @Override
    public void setContent(Map<K, V> m)
    {
        heap = m;
    }

    public Set<Map.Entry<K, V>> entrySet()
    {
        return heap.entrySet();
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        if(heap.isEmpty()) {
            buff.append("Heap: empty\n");
        }
        else {
            buff.append("Heap: \n");
            for(Map.Entry<K,V> entry : heap.entrySet()) {
                buff.append(entry.getKey());
                buff.append(" --> ");
                buff.append(entry.getValue());
                buff.append("\n");
            }
        }
        return buff.toString();
    }
}
