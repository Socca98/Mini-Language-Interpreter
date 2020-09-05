package model.containers;

import java.util.Set;

public interface ILockTable {
    int get(int key);

    int put(int value);

    void put(int key, int value);

    boolean contains(int key);

    Set<Integer> keySet();
}

