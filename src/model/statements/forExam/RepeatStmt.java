package model.statements.forExam;

import model.containers.IDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.InexistentSymbolException;
import model.exceptions.TypeException;
import model.expressions.BoolExpressions.DifferentExpr;
import model.expressions.BoolExpressions.EqualsExpr;
import model.expressions.BoolExpressions.NotExpr;
import model.expressions.IExpression;
import model.expressions.ValueExpr;
import model.program.PrgState;
import model.statements.IStmt;
import model.statements.WhileStmt;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

//aka doWhile{}; RepeatStmt(new PrintStmt(..), new Va)
public class RepeatStmt implements IStmt {
    private IStmt stmt;
    private IExpression exp;

    public RepeatStmt(IStmt stmt, IExpression exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        state.getExeStack().push(
                new WhileStmt(new NotExpr(exp), stmt)
        );
        state.getExeStack().push(stmt);

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        return null;
    }

    @Override
    public String toString() {
        return "RepeatStmt{" +
                "stmt=" + stmt +
                ", exp=" + exp +
                '}';
    }
}
