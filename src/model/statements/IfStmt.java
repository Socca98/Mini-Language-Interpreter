package model.statements;

import model.exceptions.DivisionByZeroException;
import model.exceptions.TypeException;
import model.exceptions.InexistentSymbolException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.types.BoolType;
import model.containers.IDictionary;
import model.containers.IExeStack;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStmt implements IStmt {
    private IExpression cond;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(IExpression e, IStmt t, IStmt el) {
        this.cond = e;
        this.thenS = t;
        this.elseS = el;
    }

    @Override
    public PrgState execute(PrgState p) throws TypeException, DivisionByZeroException, InexistentSymbolException {
        IDictionary<String, Value> dict = p.getSymbolT();
        IExeStack<IStmt> stack = p.getExeStack();

        try {
            Value res = this.cond.eval(dict, p.getHeap());

            if (!res.getType().equals(new BoolType()))
                throw new TypeException("The If condition must be bool!\n");

            BoolValue i1 = (BoolValue) res;
            if (!i1.getVal())
                stack.push(this.elseS);
            else
                stack.push(this.thenS);
            return null;

        } catch (DivisionByZeroException | InexistentSymbolException | TypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return "IF(" + this.cond + ") THEN (" + this.thenS + "); ELSE(" + this.elseS + ");";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typexp = cond.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            IDictionary<String, Type> thenEnv, elseEnv;
            thenEnv = thenS.typecheck(typeEnv);
            elseEnv = elseS.typecheck(typeEnv);
            return typeEnv;
        } else
            throw new TypeException("The condition of IF does not have type bool.");
    }
}
