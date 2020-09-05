package utils;

import controller.Controller;
import model.containers.*;
import model.expressions.ArithmeticExpr;
import model.expressions.ValueExpr;
import model.expressions.VarExpr;
import model.files.CloseRFile;
import model.files.FileTable;
import model.files.OpenRFile;
import model.files.ReadFile;
import model.heap.HAllocation;
import model.heap.HRead;
import model.heap.HWrite;
import model.program.PrgState;
import model.statements.*;
import model.statements.forExam.ForStmt;
import model.statements.synchronization.Lock;
import model.statements.synchronization.NewLock;
import model.statements.synchronization.Unlock;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.types.Type;
import model.values.IntValue;
import model.values.RefValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ProgramGenerator {

    public static List<Controller> generatePrograms() {
        IStmt stmt0 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("varf", new StringType()),
                        new CompStmt(
                                new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))),
                                new CompStmt(
                                        new OpenRFile(new VarExpr("varf")),
                                        new CompStmt(
                                                new AssignStmt("varf", new ValueExpr(new StringValue("test2.in"))),
                                                new CompStmt(
                                                        new OpenRFile(new VarExpr("varf")),
                                                        new PrintStmt(new VarExpr("varf"))

                                                )
                                        )
                                )
                        )
                )
        );
        PrgState prg0 = new PrgState(PrgState.generateNewId(),  //<-important!! else first thread created has id 1 same as main thread
                new ExeStack<>(),
                new Dictionary<>(),
                new OutputList<>(),
                stmt0,
                new FileTable<>(),
                new Heap<>(),
                new LockTable());
        IRepository repo0 = new Repository(prg0, "log0.txt");
        Controller ctrl0 = new Controller(repo0);

        /*
        int v;
        Ref int a;
        v=10;
        new(a,22);
        fork(wH(a,30);v=32;print(v);print(rH(a)));
        print(v);
        print(rH(a))
        */
        IStmt stmt1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExpr(new IntValue(10))),
                                new CompStmt(
                                        new HAllocation("a", new ValueExpr(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new HWrite("a", new ValueExpr(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExpr(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExpr("v")),
                                                                                new PrintStmt(new HRead(new VarExpr("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExpr("v")),
                                                        new PrintStmt(new HRead(new VarExpr("a")))
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState prg1 = new PrgState(1,
                new ExeStack<>(),
                new Dictionary<>(),
                new OutputList<>(),
                stmt1,
                new FileTable<>(),
                new Heap<>(),
                new LockTable());
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctrl1 = new Controller(repo1);

        /* To see GC in action
        Ref int v;
        new(v,20);
        Ref Ref int a;
        new(a,v);
        new(v,30);
        print(rH(rH(a)))
        new(v,99);
         */
        IStmt stmt2 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new HAllocation("v", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new HAllocation("a", new VarExpr("v")),
                                        new CompStmt(
                                                new HAllocation("v", new ValueExpr(new IntValue(30))),
                                                new CompStmt(
                                                        new HAllocation("v", new ValueExpr(new IntValue(99))),
                                                        new PrintStmt(new HRead(new HRead(new VarExpr("a"))))
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState prg2 = new PrgState(1,  //generate now so future threads will not start with 1
                new ExeStack<>(),
                new Dictionary<>(),
                new OutputList<>(),
                stmt2,
                new FileTable<>(),
                new Heap<>(),
                new LockTable());
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctrl2 = new Controller(repo2);

        /*
        string varf;
        varf="test.in";
        openRFile(varf);
        int varc;
        readFile(varf,varc);print(varc);
        readFile(varf,varc);print(varc)
        closeRFile(varf)
         */
        IStmt stmt3 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFile(new VarExpr("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFile(new VarExpr("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExpr("varc")),
                                                        new CompStmt(
                                                                new ReadFile(new VarExpr("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExpr("varc")),
                                                                        new CloseRFile(new VarExpr("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState prg3 = new PrgState(1,  //generate now so future threads will not start with 1
                new ExeStack<>(),
                new Dictionary<>(),
                new OutputList<>(),
                stmt3,
                new FileTable<>(),
                new Heap<>(),
                new LockTable());
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);

        /*
        string varf;
        varf="test.in";
        openRFile(varf);
        int varc;
        readFile(varf,varc);print(varc);
        readFile(varf,varc);print(varc)
        closeRFile(varf)
         */
        IStmt stmt4 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new AssignStmt("a", new ValueExpr(new IntValue(44))),
                        new CompStmt(
                                new AssignStmt( //a=int, hread=ref - incompatible types
                                        "a",
                                        new HRead(new ValueExpr(new RefValue()))),  //a=int, hread requires ref - wrong type parameter
                                new PrintStmt(new ValueExpr(new RefValue(2, new StringType())))
                        )
                )
        );
        PrgState prg4 = new PrgState(1,  //generate now so future threads will not start with 1
                new ExeStack<>(),
                new Dictionary<>(),
                new OutputList<>(),
                stmt4,
                new FileTable<>(),
                new Heap<>(),
                new LockTable());
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctrl4 = new Controller(repo4);

        /*
        Ref int v1;
        Ref int v2;
        int x;
        int q;
        new(v1,20);
        new(v2,30);
        newLock(x);

        fork(
         fork(
         lock(x);
         wh(v1, rh(v1) - 1);
         unlock(x);
         ); endFork
         lock(x);
         wh(v1, rh(v1) * 10);
         unlock(x);
        ); endFork

        newLock(q);
        fork(
         fork(
            lock(q);
            wh(v2,rh(v2)+5);
            unlock(q)
         );
         lock(q);
         wh(v2, rh(v2) * 10);
         unlock(q);
        );
        nop; nop; nop; nop;
        lock(x); print(rh(v1)); unlock(x);
        lock(q); print(rh(v2)); unlock(q);
        */
        IStmt stmt5 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("x", new IntType()),
                                new CompStmt(
                                        new VarDeclStmt("q", new IntType()),
                                        new CompStmt(
                                                new HAllocation("v1", new ValueExpr(new IntValue(20))),
                                                new CompStmt(
                                                        new HAllocation("v2", new ValueExpr(new IntValue(30))),
                                                        new CompStmt(
                                                                new NewLock("x"),
                                                                new CompStmt(
                                                                        new ForkStmt(new CompStmt(
                                                                                new ForkStmt(new CompStmt(
                                                                                        new Lock("x"),
                                                                                        new CompStmt(
                                                                                                new HWrite("v1",
                                                                                                        new ArithmeticExpr('-',
                                                                                                                new HRead(new VarExpr("v1")),
                                                                                                                new ValueExpr(new IntValue(1)))),
                                                                                                new Unlock("x")
                                                                                        )
                                                                                )),
                                                                                new CompStmt(
                                                                                        new Lock("x"),
                                                                                        new CompStmt(
                                                                                                new HWrite("v1",
                                                                                                        new ArithmeticExpr('*',
                                                                                                                new HRead(new VarExpr("v1")),
                                                                                                                new ValueExpr(new IntValue(10)))),
                                                                                                new Unlock("x")
                                                                                        )
                                                                                )
                                                                        )),
                                                                        new CompStmt(
                                                                                new NewLock("q"),
                                                                                new CompStmt(
                                                                                        new ForkStmt(new CompStmt(
                                                                                                new ForkStmt(new CompStmt(
                                                                                                        new Lock("q"),
                                                                                                        new CompStmt(
                                                                                                                new HWrite("v2",
                                                                                                                        new ArithmeticExpr('+',
                                                                                                                                new HRead(new VarExpr("v2")),
                                                                                                                                new ValueExpr(new IntValue(5)))),
                                                                                                                new Unlock("q")
                                                                                                        )
                                                                                                )),
                                                                                                new CompStmt(
                                                                                                        new Lock("q"),
                                                                                                        new CompStmt(
                                                                                                                new HWrite("v2",
                                                                                                                        new ArithmeticExpr('*',
                                                                                                                                new HRead(new VarExpr("v2")),
                                                                                                                                new ValueExpr(new IntValue(10)))),
                                                                                                                new Unlock("q")
                                                                                                        )
                                                                                                )
                                                                                        )),
                                                                                        new CompStmt(
                                                                                                new Nop(),
                                                                                                new CompStmt(
                                                                                                        new Nop(),
                                                                                                        new CompStmt(
                                                                                                                new Nop(),
                                                                                                                new CompStmt(
                                                                                                                        new Nop(),
                                                                                                                        new CompStmt(
                                                                                                                                new Lock("x"),
                                                                                                                                new CompStmt(
                                                                                                                                        new PrintStmt(new HRead(new VarExpr("v1"))),
                                                                                                                                        new CompStmt(
                                                                                                                                                new Unlock("x"),
                                                                                                                                                new CompStmt(
                                                                                                                                                        new Lock("q"),
                                                                                                                                                        new CompStmt(
                                                                                                                                                                new PrintStmt(new HRead(new VarExpr("v2"))),
                                                                                                                                                                new Unlock("q")
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState prg5 = new PrgState(1,  //generate now so future threads will not start with 1
                new ExeStack<>(),
                new Dictionary<>(),
                new OutputList<>(),
                stmt5,
                new FileTable<>(),
                new Heap<>(),
                new LockTable());
        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller ctrl5 = new Controller(repo5);

        /*
        Ref int a;
        new(a,20);
        (for(v=0;v<3;v=v+1)
            fork( print(v);v=v*rh(a)) );
        print(rh(a))
         */
        IStmt stmt6 = new CompStmt(
                new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(
                        new HAllocation("a", new ValueExpr(new IntValue(20))),
                        new CompStmt(
                                new ForStmt("v",
                                        new ValueExpr(new IntValue(0)),
                                        new ValueExpr(new IntValue(3)),
                                        new ArithmeticExpr('+',
                                                new VarExpr("v"),
                                                new ValueExpr(new IntValue(1))
                                        ),
                                        new ForkStmt(new CompStmt(
                                                new PrintStmt(new VarExpr("v")),
                                                new AssignStmt("v",
                                                        new ArithmeticExpr('*',
                                                                new VarExpr("v"),
                                                                new HRead(new VarExpr("a")))
                                                ))
                                        )
                                ),
                                new PrintStmt(new HRead(new VarExpr("a")))
                        )
                )
        );
        PrgState prg6 = new PrgState(1,  //generate now so future threads will not start with 1
                new ExeStack<>(),
                new Dictionary<>(),
                new OutputList<>(),
                stmt6,
                new FileTable<>(),
                new Heap<>(),
                new LockTable());
        IRepository repo6 = new Repository(prg6, "log6.txt");
        Controller ctrl6 = new Controller(repo6);


        //Type checking - throws TypeException
        IDictionary<String, Type> typeDictionary = new Dictionary<>();
//        stmt0.typecheck(typeDictionary);
//        stmt1.typecheck(typeDictionary);
//        stmt2.typecheck(typeDictionary);
//        stmt3.typecheck(typeDictionary);
//        stmt4.typecheck(typeDictionary);
        stmt6.typecheck(typeDictionary);
        stmt5.typecheck(typeDictionary);

        //Constructing list for GUI
        List<Controller> generated = new ArrayList<>();
        generated.add(ctrl5);
        generated.add(ctrl6);
        generated.add(ctrl1);
        generated.add(ctrl2);
        generated.add(ctrl3);
        generated.add(ctrl4);
        generated.add(ctrl0);

        return generated;
    }

}



