package model.expressions;

import model.containers.IDictionary;
import model.containers.IHeap;
import model.exceptions.TypeException;
import model.types.Type;
import model.values.Value;

//this was once upon a time called 'ConstExpr' being only integer. Now it can be strings, number..
public class ValueExpr implements IExpression {
    private Value value;

    public ValueExpr(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public Value eval(IDictionary<String, Value> dict, IHeap<Integer,Value> heap) {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public Type typecheck(IDictionary<String,Type> typeEnv) throws TypeException {
        return value.getType();
    }
}
