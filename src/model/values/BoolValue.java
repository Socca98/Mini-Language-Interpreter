package model.values;

import model.types.BoolType;
import model.types.Type;

public class BoolValue implements Value {
    private boolean val;

    public BoolValue() {
        val = false;
    }

    public BoolValue(boolean v) {
        val = v;
    }

    public boolean getVal() {
        return val;
    }

    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolValue;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
