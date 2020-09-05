package model.heap;

import model.exceptions.TypeException;
import model.exceptions.HeapException;
import model.expressions.IExpression;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

//THIS IS EXPRESSION, OTHERS ARE STATEMENTS
public class HRead implements IExpression {
    private IExpression expr;

    public HRead(IExpression expr) {
        this.expr = expr;
    }

    /**
     * it is a statement which takes a variable and an expression, the variable contains the heap
     * address, the expression represents the new value that is going to be stored into the heap
     *
     * @param symTable symbol table
     * @param heap     heap
     * @return value
     */
    @Override
    public Value eval(IDictionary<String, Value> symTable, IHeap<Integer, Value> heap) {
        Value value = expr.eval(symTable, heap);

        if (!(value instanceof RefValue))
            throw new TypeException("In ReadHeap, expression is not reference type!");

        RefValue trueValue = (RefValue) value;
        int addr = trueValue.getAddr();
        if (!heap.contains(addr))
            throw new HeapException("In ReadHeap, the address from expression does not exist!");

        return heap.get(addr);
    }

    @Override
    public String toString() {
        return "rH(" + expr + ")";
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typ = expr.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType refType = (RefType) typ;
            return refType.getInner();
        } else
            throw new TypeException("the rH argument is not a Ref Type");
    }
}
