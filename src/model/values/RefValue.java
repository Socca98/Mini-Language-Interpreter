package model.values;

import model.types.RefType;
import model.types.Type;

public class RefValue implements Value {
    private int address;
    private Type locationType;

    public RefValue() {
    }

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return address;
    }

    public Type getType() {
        return new RefType(locationType);
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setLocationType(Type locationType) {
        this.locationType = locationType;
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof RefValue;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}
