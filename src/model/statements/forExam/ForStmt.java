package model.statements.forExam;

import model.containers.IDictionary;
import model.containers.IExeStack;
import model.containers.IHeap;
import model.exceptions.DivisionByZeroException;
import model.exceptions.InexistentSymbolException;
import model.exceptions.TypeException;
import model.expressions.BoolExpressions.LessExpr;
import model.expressions.IExpression;
import model.expressions.VarExpr;
import model.program.PrgState;
import model.statements.*;
import model.types.IntType;
import model.types.Type;
import model.values.Value;

//for(v = exp1; v < exp2; v = exp3) stmt;
//ForStmt(index, exp1, exp2, exp3, stmt);
public class ForStmt implements IStmt {
    private String index;
    private IExpression exp1;
    private IExpression exp2;
    private IExpression exp3;
    private IStmt stmt;

    public ForStmt(String index, IExpression exp1, IExpression exp2, IExpression exp3, IStmt stmt) {
        this.index = index;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        IExeStack<IStmt> stack = state.getExeStack();

        IStmt s1 = new VarDeclStmt("v", new IntType());
        IStmt s2 = new AssignStmt("v", exp1);
        IStmt s3 = new WhileStmt(
                new LessExpr(new VarExpr("v"), exp2),
                new CompStmt(
                        stmt,
                        new AssignStmt("v", exp3)
                )
        );

        stack.push(s3);
        stack.push(s2);
        stack.push(s1);

//        stack.push(
//                new CompStmt(
//                        new AssignStmt(index, exp1),
//                        new WhileStmt(
//                                new LessExpr(
//                                        new VarExpr(index),
//                                        exp2
//                                ),
//                                new CompStmt(
//                                        stmt,
//                                        new AssignStmt(
//                                                index,
//                                                exp3
//                                        )
//                                )
//                        )
//                )
//        );

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        typeEnv.add(index, new IntType());
        Type type1 = typeEnv.get(index);
        Type type2 = exp1.typecheck(typeEnv);
        Type type3 = exp2.typecheck(typeEnv);
        Type type4 = exp3.typecheck(typeEnv);
        stmt.typecheck(typeEnv);
        if(type1.equals(new IntType()) &&
            type2.equals(new IntType()) &&
            type3.equals(new IntType()) &&
            type4.equals(new IntType()))
            return typeEnv;
        else
            throw new TypeException("For: the parameters are not int type!");
    }

    //for(v = exp1; v < exp2; v = exp3) stmt;
    @Override
    public String toString() {
        return "for(" + index + "=" + exp1 + "; " + index + "<" + exp2 + "; " + index + "=" + exp3 + ")\n" + stmt + "\nendFor;";
    }
}
