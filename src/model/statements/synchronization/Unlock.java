package model.statements.synchronization;

import model.containers.IDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.EvaluationException;
import model.exceptions.InexistentSymbolException;
import model.exceptions.TypeException;
import model.program.PrgState;
import model.statements.IStmt;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

//int x; unlock(x);
public class Unlock implements IStmt {
    private String varName;
    private static java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();

    public Unlock(String varName) {
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        lock.lock();
        try {
            if (!state.getSymbolT().contains(varName))
                throw new EvaluationException("Lock: var does not exist!");

            Value foundIndex = state.getSymbolT().get(varName);
            if (!foundIndex.getType().equals(new IntType()))
                throw new EvaluationException("Lock: foundIndex not int type!");

            IntValue intVal = (IntValue) foundIndex;
            int idLock = intVal.getVal();

            if (!state.getLockTable().contains(idLock))
                throw new EvaluationException("Lock: idLock does not exist in Lock Table!");

            if (state.getLockTable().get(idLock) == state.getId())
                state.getLockTable().put(idLock, -1);

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type varType = typeEnv.get(varName);
        if (varType.equals(new IntType()))
            return typeEnv;
        throw new TypeException("Unlock: variable not int type!");
    }

    @Override
    public String toString() {
        return "unlock(" + varName + ")";
    }
}
