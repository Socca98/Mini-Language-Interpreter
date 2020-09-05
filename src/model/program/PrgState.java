package model.program;

import model.containers.*;
import model.statements.IStmt;
import model.exceptions.EmptyCollectionException;
import model.files.IFileTable;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {
    private int id;
    private IExeStack<IStmt> exeStack;
    private IDictionary<String, Value> symTable;
    private IList<Value> out;
    private IFileTable<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, Value> heap;
    private ILockTable lockTable;
    private static AtomicInteger newId = new AtomicInteger(1);
    private IStmt originalStatement; //keep the original form of the program, so we can display it in the GUI

    public PrgState(int id,
                    IExeStack<IStmt> stk,
                    IDictionary<String, Value> symbolTable,
                    IList<Value> ot,
                    IStmt statement,
                    IFileTable<StringValue, BufferedReader> ft,
                    IHeap<Integer, Value> _heap,
                    ILockTable _lockTable) {
        this.id = id;
        this.exeStack = stk;
        this.symTable = symbolTable;
        this.out = ot;
        this.fileTable = ft;
        this.heap = _heap;
        this.originalStatement = statement;
        this.lockTable = _lockTable;

        this.exeStack.push(statement);
    }

    /**
     * Performs one execution of the next/top statement on the execution stack from the parameter 'state'.
     */
    public PrgState oneStep() {
        IExeStack<IStmt> stack = getExeStack();
        if(exeStack.isEmpty())
            throw new EmptyCollectionException("Program state's stack is empty");
        IStmt stmt = stack.pop();
        return stmt.execute(this);    //execute the statement on top of the execution stack
    }

    public static int generateNewId() { return newId.getAndIncrement(); } //returns it and then increments

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public int getId() {
        return id;
    }

    public IExeStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public IDictionary<String, Value> getSymbolT() {
        return this.symTable;
    }

    public IList<Value> getOutputList() {
        return this.out;
    }

    public IFileTable<StringValue, BufferedReader> getFileTable()
    {
        return fileTable;
    }

    public IHeap<Integer, Value> getHeap() {
        return heap;
    }

    public ILockTable getLockTable() {
        return lockTable;
    }

    public String originalStatementToString() {
        return originalStatement.toString();
    }

    public String toString() {
        return "ID: " + this.id + "\n" +
                this.exeStack +
                this.symTable +
                this.out +
                this.fileTable +
                this.heap +
                "\n";
    }
}
