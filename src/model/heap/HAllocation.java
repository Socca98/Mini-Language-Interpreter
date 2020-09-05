package model.heap;

import model.statements.IStmt;
import model.exceptions.TypeException;
import model.exceptions.InexistentSymbolException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.types.RefType;
import model.containers.IDictionary;
import model.containers.IHeap;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

//new(var_name, expression); new("a", new ValueExpr(new IntValue(2)));
public class HAllocation implements IStmt {
    private String var;
    private IExpression expr;

    public HAllocation(String variableName, IExpression exp) {
        var = variableName;
        expr = exp;
    }

    @Override
    public PrgState execute(PrgState state) {
        IDictionary<String, Value> symTable = state.getSymbolT();
        IHeap<Integer, Value> heap = state.getHeap();

        //check whether var_name is a variable in SymTable and its type is a RefType
        if (!symTable.contains(var))
            throw new InexistentSymbolException("In New, this variable does not exist!");
        Value valueVariable = symTable.get(var);
        if (!(valueVariable.getType() instanceof RefType))
            throw new TypeException("In New, variable in symbol table is not reference type!");

        //Evaluate the expression to a value and then compare the type of the value to the
        //locationType from the value associated to var_name in SymTable. If the types are not equal,
        //the execution is stopped with an appropriate error message.
        RefValue valueVariableConverted = (RefValue) valueVariable;
        RefType typeOfValueVariable = (RefType) valueVariableConverted.getType();
        Value expressionValue = expr.eval(symTable, state.getHeap());

        if (!typeOfValueVariable.getInner().equals(expressionValue.getType()))
            throw new TypeException("In New, the variable name and the expression do not match type!");

        //Create a new entry in the Heap table such that a new key (new free address) is generated and
        //it is associated to the result of the expression evaluation
        int nextFreeLocation = HIDGenerator.getID();
        heap.add(nextFreeLocation, expressionValue);

        //in SymTable update the RefValue associated to the var_name such that the new RefValue
        //has the same locationType and the address is equal to the new key generated in the Heap at
        //the previous step
//        valueVariableConverted.setAddress(nextFreeLocation);
        symTable.add(var, new RefValue(nextFreeLocation, typeOfValueVariable.getInner()));

        return null;
    }

    @Override
    public String toString() {
        return "newH(" + var + ", " + expr + ");";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typevar = typeEnv.get(var);
        Type typexp = expr.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new TypeException("HAllocation: right hand side and left hand side have different types");
    }
}
