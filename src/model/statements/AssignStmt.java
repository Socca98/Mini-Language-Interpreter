package model.statements;

import model.exceptions.DivisionByZeroException;
import model.exceptions.TypeException;
import model.exceptions.InexistentSymbolException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.types.Type;
import model.containers.IDictionary;
import model.values.Value;

public class AssignStmt implements IStmt {
    private String varName;
    private IExpression expr;

    public AssignStmt(String VarName, IExpression Expr) {
        this.varName = VarName;
        this.expr = Expr;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        IDictionary<String, Value> d = state.getSymbolT();

        try {
            Value instr = this.expr.eval(d, state.getHeap());

            if (!d.contains(this.varName))
                throw new TypeException("This variable is not declared in the symbol table!\n");

            Type typId = d.get(varName).getType();
            //check if type of varName is the same as expr:   a = 1+2, a-int;  b = true, b-bool
            if (!instr.getType().equals(typId))
                throw new TypeException("Declared type of variable" + typId + "and type of " +
                        "the assigned expression do not match!\n");

            d.add(this.varName, instr);

        } catch (DivisionByZeroException | InexistentSymbolException | TypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return varName + " = " + expr + "; ";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typevar = typeEnv.get(varName);
        Type typexp = expr.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new TypeException("Assignment: right hand side and left hand side have different types.");
    }
}

