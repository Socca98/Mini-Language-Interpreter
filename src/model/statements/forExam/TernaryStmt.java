package model.statements.forExam;

import model.containers.IDictionary;
import model.containers.IExeStack;
import model.exceptions.DivisionByZeroException;
import model.exceptions.InexistentSymbolException;
import model.exceptions.TypeException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.statements.AssignStmt;
import model.statements.IStmt;
import model.statements.IfStmt;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

//c = (false)?100:200;
public class TernaryStmt implements IStmt {
    private String varName;
    private IExpression cond;
    private IExpression exp1;
    private IExpression exp2;

    public TernaryStmt(String varName, IExpression cond, IExpression exp1, IExpression exp2) {
        this.varName = varName;
        this.cond = cond;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public PrgState execute(PrgState p) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        IExeStack<IStmt> stack = p.getExeStack();

        IStmt stmt = new IfStmt(cond,
                new AssignStmt(varName, exp1),
                new AssignStmt(varName, exp2)
        );

        stack.push(stmt);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typeCond = cond.typecheck(typeEnv);
        if (typeCond.equals(new BoolType())) {
            Type typeExp1 = exp1.typecheck(typeEnv);
            Type typeExp2 = exp2.typecheck(typeEnv);
            Type typeVar = typeEnv.get(varName);
            if (typeVar.equals(typeExp1) && typeVar.equals(typeExp2))
                return typeEnv;
            else
                throw new TypeException("Ternary: variable and expressions are not the same type!");
        } else
            throw new TypeException("Ternary: the condition does not have type bool!");
    }

    @Override
    public String toString() {
        return varName + " = " + cond + " ? " + exp1 + " : " + exp2 + ";";
    }
}
