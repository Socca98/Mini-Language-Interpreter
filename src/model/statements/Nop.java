package model.statements;

import model.containers.IDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.TypeException;
import model.exceptions.InexistentSymbolException;
import model.program.PrgState;
import model.types.Type;

public class Nop implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        //the pop from Controller.OneStep() removes 'Nop' from execution stack from this parameter
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Nop;";
    }
}
