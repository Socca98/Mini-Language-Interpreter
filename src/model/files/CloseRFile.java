package model.files;

import model.containers.IDictionary;
import model.statements.IStmt;
import model.exceptions.TypeException;
import model.exceptions.FileException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.types.BoolType;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.IOException;

//closeRFile(new VarExpr("test.in"))
public class CloseRFile implements IStmt {
    private IExpression exp;    //exp.eval() must be StringValue and only VarExpr can return StringValue

    public CloseRFile(IExpression e) {
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state) {
        try {
            Value val = exp.eval(state.getSymbolT(), state.getHeap());

            //Check if the expression in the statement is of StringType
            if(!val.getType().equals(new StringType()))
                throw new TypeException("CloseRFile has no StringType argument!");

            StringValue stringFileName = (StringValue) val;

            //Check if file exists in fileTable
            if (!state.getFileTable().contains(stringFileName))
                throw new FileException("In CloseRFile, the StringValue does not exist in the File Table! Inexistent file!");

            state.getFileTable().get(stringFileName).close();
            state.getFileTable().remove(stringFileName);

        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "closeFile(" + exp + ")";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new TypeException("CloseRFile: expression is not type string.");
    }
}
