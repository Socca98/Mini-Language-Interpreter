package model.expressions;

import model.exceptions.TypeException;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.types.Type;
import model.values.Value;

public class VarExpr implements IExpression {
    private String variableName;

    public VarExpr(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Value eval(IDictionary<String, Value> dict, IHeap<Integer,Value> heap) {
        if (dict.contains(variableName))
            return dict.get(variableName);
        else
            throw new TypeException("Variable does not exist!");
    }

    public String toString() {
        return variableName;
    }

    public Type typecheck(IDictionary<String,Type> typeEnv) throws TypeException{
        return typeEnv.get(variableName);
    }
}
