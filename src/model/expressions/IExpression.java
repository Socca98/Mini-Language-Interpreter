package model.expressions;

import model.containers.IDictionary;
import model.containers.IHeap;
import model.exceptions.TypeException;
import model.types.Type;
import model.values.Value;

public interface IExpression {
    Value eval(IDictionary<String, Value> dict, IHeap<Integer,Value> heap);
    Type typecheck(IDictionary<String, Type> typeEnv) throws TypeException;
}
