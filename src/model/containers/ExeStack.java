package model.containers;

import java.util.ArrayDeque;
import java.util.Deque;

public class ExeStack<T> implements IExeStack<T>{
    private Deque<T> stack = new ArrayDeque<>();

    public void push(T el) {
        this.stack.addFirst(el);
    }

    public T pop() {
        return this.stack.pop();
    }

    public boolean isEmpty() {
        return this.stack.size() == 0;
    }

    public Iterable<T> getAll() {
        return this.stack;
    }

    public T top() {
        return this.stack.getFirst();
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        if (this.stack.isEmpty())
            buff.append("Stack: empty\n");
        else {
            buff.append("Stack:\n");

            for (T i : this.stack) {
                buff.append(i);
                buff.append("\n");
            }
        }
        return buff.toString();
    }
}
