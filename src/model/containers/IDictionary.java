package model.containers;

import java.util.Collection;

public interface IDictionary<K, V> {
    void add(K key, V value);

    void update(K key, V value);

    boolean contains(K key);

    V get(K var1);

    Iterable<K> getAll();

    Collection<V> getAllValues();

    IDictionary<K, V> deepCopy();
}
