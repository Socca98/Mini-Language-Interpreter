package model.files;

public interface IFileTable<K, V> {
    void add(K key, V val);
    void remove(K key);
    boolean contains(K key);
    V get(K key);
    Iterable<K> getAll();
    Iterable<V> getValues();
}
