package model.containers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LockTable implements ILockTable {
    private Map<Integer, Integer> content;
    private int id;

    public LockTable() {
        content = new HashMap<>();
        id = 0;
    }

    @Override
    public int get(int key) {
        System.out.println("searching for " + key);
        if (content.get(key) == null) {
            System.out.println("null");
        }
        System.out.println(content.keySet());
        return content.get(key);
    }

    @Override
    public int put(int value) {
        content.put(++id, value);
        return id;
    }

    @Override
    public void put(int key, int value) {
        content.put(key, value);
    }

    @Override
    public boolean contains(int key) {
        return content.containsKey(key);
    }


    @Override
    public Set<Integer> keySet() {
        return content.keySet();
    }
}
