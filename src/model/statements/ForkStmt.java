package model.statements;

import model.exceptions.TypeException;
import model.program.PrgState;
import model.containers.ExeStack;
import model.containers.IDictionary;
import model.containers.IExeStack;
import model.types.Type;
import model.values.Value;

public class ForkStmt implements IStmt {
    private IStmt childStatement; //This statement will be passed to the newly created child process

    public ForkStmt(IStmt childStatement) {
        this.childStatement = childStatement;
    }

    @Override
    public PrgState execute(PrgState state) {
        IDictionary<String, Value> childSymTable = state.getSymbolT().deepCopy(); //deepCopy is implemented in this Assignment with Threads
        IExeStack<IStmt> childExeStack = new ExeStack<>();
        //Child statement is pushed by the constructor of the PrgState onto the Execution Stack
        return new PrgState(PrgState.generateNewId(), childExeStack, childSymTable, state.getOutputList(), childStatement, state.getFileTable(), state.getHeap(), state.getLockTable());
    }

    public String toString() {
        return "fork(" + childStatement + ")\nendFork;";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        return typeEnv;
    }
}
