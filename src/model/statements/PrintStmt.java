package model.statements;

import model.containers.IDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.TypeException;
import model.exceptions.InexistentSymbolException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.types.Type;
import model.values.Value;

public class PrintStmt implements IStmt{
    private IExpression expr;

    public PrintStmt(IExpression expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        try {
            Value res = expr.eval(state.getSymbolT(), state.getHeap());
            state.getOutputList().add(res);
        } catch (DivisionByZeroException | InexistentSymbolException | TypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return "print(" + this.expr + ");";
    }

    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        expr.typecheck(typeEnv);
        return typeEnv;
    }

}
