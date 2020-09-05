package model.types;

import model.values.BoolValue;
import model.values.Value;

public class BoolType implements Type {

    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    public String toString() {
        return "boolean";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue();
    }
}
