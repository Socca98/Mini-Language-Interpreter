package model.expressions.BoolExpressions;

import model.exceptions.TypeException;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.IntType;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class EqualsExpr implements IExpression {
    private IExpression left, right;

    public EqualsExpr(IExpression l, IExpression r) {
        left = l;
        right = r;
    }

    @Override
    public Value eval(IDictionary<String, Value> dict, IHeap<Integer,Value> heap) {
        Value leftInt = left.eval(dict, heap);
        Value rightInt = right.eval(dict, heap);

        //Check if they are integers
        if(!leftInt.getType().equals(new IntType()))
            throw new TypeException("Left side of = is not integer!");
        if(!rightInt.getType().equals(new IntType()))
            throw new TypeException("Right side of = is not integer!");

        //Get the integer values from 'Value' objects
        IntValue i1 = (IntValue)leftInt;
        IntValue i2 = (IntValue)rightInt;
        int n1,n2;
        n1 = i1.getVal();
        n2 = i2.getVal();

        if (n1 == n2)
            return new BoolValue(true);
        return new BoolValue(false);
    }

    @Override
    public String toString()
    {
        return "(" + left + "==" + right + ")";
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typ1, typ2;
        typ1 = left.typecheck(typeEnv);
        typ2 = right.typecheck(typeEnv);
        if (typ1.equals(new IntType()))
            if (typ2.equals(new IntType()))
                return new BoolType();
            else
                throw new TypeException("second operand is not an integer");
        else
            throw new TypeException("first operand is not an integer");
    }
}
