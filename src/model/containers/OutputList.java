package model.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OutputList<T> implements IList<T> {
    private List<T> l = new ArrayList<>();

    public void add(T x) {
        this.l.add(x);
    }

    public Iterable<T> getAll() {
        return l;
    }

    public String toString() {
        return "Output list: " + this.l + "\n";
    }
}