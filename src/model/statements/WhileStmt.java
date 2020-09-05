package model.statements;

import model.containers.IDictionary;
import model.exceptions.TypeException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.types.BoolType;
import model.containers.IExeStack;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class WhileStmt implements IStmt {
    private IExpression cond;
    private IStmt stmt;

    public WhileStmt(IExpression c, IStmt s) {
        cond = c;
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) {
        IExeStack<IStmt> stack = state.getExeStack();

        Value res = cond.eval(state.getSymbolT(), state.getHeap());

        if(!res.getType().equals(new BoolType()))
            throw new TypeException("In While, condition is not bool type!");

        BoolValue i1 = (BoolValue)res;
        if(!i1.getVal())
            return null;

        stack.push(this);
        stack.push(stmt);
        return null;
    }

    @Override
    public String toString() {
        return "WHILE(" + cond + "): " + stmt + "";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typexp = cond.typecheck(typeEnv);
        if (typexp.equals(new BoolType()))
            return typeEnv;
        else
            throw new TypeException("The condition of WHILE does not have type bool.");
    }
}


