package model.values;

import model.types.IntType;
import model.types.Type;

public class IntValue implements Value {
    private int val;

    public IntValue() {
        val = 0;
    }

    public IntValue(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }

    public Type getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof IntValue;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}

