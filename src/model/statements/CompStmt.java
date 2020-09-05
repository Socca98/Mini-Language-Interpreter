package model.statements;

import model.containers.IDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.TypeException;
import model.exceptions.InexistentSymbolException;
import model.program.PrgState;
import model.containers.IExeStack;
import model.types.Type;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        IExeStack<IStmt> stack = state.getExeStack();
        stack.push(this.second);
        stack.push(this.first);
        return null;
    }

//    public String toString() {
//        return "(" + this.first + ";" + this.second + ")";
//    }

    public String toString() {
        return first.toString() + "\n" + second.toString();
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        //MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        //return typEnv2;
        return second.typecheck(first.typecheck(typeEnv));
    }

}
