package model.statements.synchronization;

import model.containers.IDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.InexistentSymbolException;
import model.exceptions.TypeException;
import model.program.PrgState;
import model.statements.IStmt;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;

//int x; newLock(x);
public class NewLock implements IStmt {
    private String varName;
    private static java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();

    public NewLock(String varName) {
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        lock.lock();
        try {
            System.out.println(state.getId() + "-atomicLockCreationStart");
            int lockID = state.getLockTable().put(-1);
            state.getSymbolT().update(varName, new IntValue(lockID));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(state.getId() + "-atomicLockCreationEnd");
            lock.unlock();
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type varType = typeEnv.get(varName);
        if (varType.equals(new IntType()))
            return typeEnv;
        throw new TypeException("NewLock: variable not int type!");
    }

    @Override
    public String toString() {
        return "newLock(" + varName + ")";
    }
}
