package model.containers;

public interface IExeStack<T> {
    void push(T var1);

    T pop();

    boolean isEmpty();

    String toString();

    Iterable<T> getAll();

    T top();
}
