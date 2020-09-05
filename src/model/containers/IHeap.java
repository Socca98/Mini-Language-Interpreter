package model.containers;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IHeap<K, V> {
    void add(K key, V value);
    void update(K key, V value);
    boolean contains(K key);
    V get(K key);
    Collection<V> getValueSet();
    Iterable<K> getKeySet();

    /*
    Changes the dictionary (heap) with another one. Setter.
    IN: Map<K,V>
    OUT:-
     */
    void setContent(Map<K, V> m);

    Set<Map.Entry<K, V>> entrySet();
}
