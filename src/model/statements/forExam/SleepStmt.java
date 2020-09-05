package model.statements.forExam;

import model.containers.IDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.InexistentSymbolException;
import model.exceptions.TypeException;
import model.expressions.IExpression;
import model.expressions.ValueExpr;
import model.program.PrgState;
import model.statements.IStmt;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class SleepStmt implements IStmt {
    private IExpression number;

    public SleepStmt(IExpression number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        Value val = number.eval(state.getSymbolT(), state.getHeap());
        IntValue intVal = (IntValue) val;

        if(intVal.getVal() == 0)
            return null;

        state.getExeStack().push(new SleepStmt(new ValueExpr(new IntValue(intVal.getVal() - 1))));
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type type = number.typecheck(typeEnv);
        if(type.equals(new IntType()))
            return typeEnv;
        else
            throw new TypeException("Sleep: not int type!");

    }

    @Override
    public String toString() {
        return "sleep(" + number + ")";
    }
}
