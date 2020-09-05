package model.expressions.BoolExpressions;

import model.containers.IDictionary;
import model.containers.IHeap;
import model.exceptions.TypeException;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class NotExpr implements IExpression {
    private IExpression expression;

    public NotExpr(IExpression expression) {this.expression = expression;}

    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeap<Integer, Value> heap) {
        BoolValue value = (BoolValue) this.expression.eval(symTable, heap);
        boolean b = value.getVal();
        return new BoolValue(!b);
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) {
        Type type =  this.expression.typecheck(typeEnv);
        if(type.equals(new BoolType()))
            throw new TypeException("Incompatible data types!");
        return new BoolType();
    }

    @Override
    public String toString() {return "not (" + this.expression.toString()+")";}
}
