package model.heap;

import model.statements.IStmt;
import model.exceptions.TypeException;
import model.exceptions.HeapException;
import model.exceptions.InexistentSymbolException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.types.RefType;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

// HWrite("a", new Value(Int(22)))
public class HWrite implements IStmt {
    /*
    it is a statement which takes a variable and an expression,
    the variable contains the heap address, the expression
    represents the new value that is going to be stored into the heap
     */
    private String varName;
    private IExpression expr;

    public HWrite(String name, IExpression expression) {
        varName = name;
        expr = expression;
    }

    @Override
    public PrgState execute(PrgState state) {
        IDictionary<String, Value> symbolTable = state.getSymbolT();
        IHeap<Integer, Value> heap = state.getHeap();

        //first we check if var_name is a variable defined in SymTable,
        // if its type is a Ref type and if the address from the
        // RefValue associated in SymTable is a key in Heap.
        if (!symbolTable.contains(varName))
            throw new InexistentSymbolException("In WriteHeap, variable is not defined in symbol table!\n");

        Value valueVariable = symbolTable.get(varName);
        if (!(valueVariable.getType() instanceof RefType))
            throw new TypeException("In WriteHeap, variable is not a reference type!");

        RefValue refValue = (RefValue) valueVariable;
        RefType typeOfValueVariable = (RefType) valueVariable.getType();
        Value newValue = expr.eval(symbolTable, heap);

        //Check if type of variable and expression type are the same
        if(!typeOfValueVariable.getInner().equals(newValue.getType()))
            throw new HeapException("In WriteHeap, type of variable and expression are not the same type!");

        //Check if heap location exists
        if (!heap.contains(refValue.getAddr()))
            throw new HeapException("This heap location is not allocated!\n");

        heap.add(refValue.getAddr(), newValue);

        return null;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expr + ")";
    }

    // HWrite("a", new Value(Int(22)))

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typevar = typeEnv.get(varName);
        Type typexp = expr.typecheck(typeEnv);

        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new TypeException("HAllocation: right hand side and left hand side have different types");
    }
}
