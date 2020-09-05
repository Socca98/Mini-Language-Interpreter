package model.containers;

public interface IList<T> {
    void add(T var1);

    Iterable<T> getAll();
}
