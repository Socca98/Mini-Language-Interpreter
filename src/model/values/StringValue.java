package model.values;

import model.types.StringType;
import model.types.Type;

public class StringValue implements Value{
    private String val;

    public StringValue() {
        val = "";
    }

    public StringValue(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringValue;
    }

    @Override
    public String toString() {
        return "\"" + val + "\"";
    }
}
