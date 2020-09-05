package model.statements;

import model.containers.IDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.InexistentSymbolException;
import model.exceptions.TypeException;
import model.program.PrgState;
import model.types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException;

    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException;
}
