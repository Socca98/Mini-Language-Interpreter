package model.expressions;

import model.exceptions.DivisionByZeroException;
import model.exceptions.TypeException;
import model.exceptions.InexistentSymbolException;
import model.types.BoolType;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExpr implements IExpression {
    private String operator;    //possible operations: &&, ||
    private IExpression left;
    private IExpression right;

    @Override
    public Value eval(IDictionary<String, Value> dict, IHeap<Integer,Value> heap) throws TypeException, DivisionByZeroException, InexistentSymbolException {
        Value l = this.left.eval(dict, heap);

        //Check if left side is BoolType
        if (!l.getType().equals(new BoolType()))
            throw new TypeException("Left side of LogicExpr not BoolType!");

        Value r = this.right.eval(dict, heap);

        //Check if right side is BoolType
        if (!r.getType().equals(new BoolType()))
            throw new TypeException("Right side of LogicExpr not BoolType!");

        //Return result
        BoolValue i1 = (BoolValue)l;
        BoolValue i2 = (BoolValue)r;
        boolean n1,n2;
        n1 = i1.getVal();
        n2 = i2.getVal();

        switch (this.operator) {
            case "&&":
                return new BoolValue(n1 && n2);
            case "||":
                return new BoolValue(n1 || n2);
            default:
                throw new InexistentSymbolException("Logical symbol does not exist!\n");
        }
    }

    public Type typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typ1, typ2;
        typ1 = left.typecheck(typeEnv);
        typ2 = right.typecheck(typeEnv);
        if (typ1.equals(new BoolType()))
            if (typ2.equals(new BoolType()))
                return new BoolType();
            else
                throw new TypeException("second operand is not a bool");
        else
            throw new TypeException("first operand is not a bool");
    }
}
