package model.expressions;

import model.exceptions.DivisionByZeroException;
import model.exceptions.TypeException;
import model.exceptions.InexistentSymbolException;
import model.types.IntType;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpr implements IExpression {
    private char operator;
    private IExpression left;
    private IExpression right;

    public ArithmeticExpr(char op, IExpression l, IExpression r) {
        this.operator = op;
        this.left = l;
        this.right = r;
    }

    @Override
    public Value eval(IDictionary<String, Value> dict, IHeap<Integer, Value> heap) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        Value l = this.left.eval(dict, heap);

        //Check if left side is IntegerType
        if (!l.getType().equals(new IntType()))
            throw new TypeException("Left side of arithmetic operation is not valid type!");

        Value r = this.right.eval(dict, heap);

        //Check if right side is IntegerType
        if (!r.getType().equals(new IntType()))
            throw new TypeException("Right side of arithmetic operation is not valid type!");

        //Return result
        IntValue i1 = (IntValue) l;
        IntValue i2 = (IntValue) r;
        int n1, n2;
        n1 = i1.getVal();
        n2 = i2.getVal();

        switch (this.operator) {
            case '+':
                return new IntValue(n1 + n2);
            case '-':
                return new IntValue(n1 - n2);
            case '*':
                return new IntValue(n1 * n2);
            case '/':
                if (n2 == 0) {
                    throw new DivisionByZeroException("Division by 0!\n");
                } else {
                    return new IntValue(n1 / n2);
                }
            default:
                throw new InexistentSymbolException("Symbol does not exist!\n");
        }
    }

    public String toString() {
        return this.left + " " + this.operator + " " + this.right;
    }

    public Type typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typ1, typ2;
        typ1 = left.typecheck(typeEnv);
        typ2 = right.typecheck(typeEnv);
        if (typ1.equals(new IntType()))
            if (typ2.equals(new IntType()))
                return new IntType();
            else
                throw new TypeException("second operand is not an integer");
        else
            throw new TypeException("first operand is not an integer");
    }
}
