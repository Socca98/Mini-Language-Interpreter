package model.files;

import model.containers.IDictionary;
import model.statements.IStmt;
import model.exceptions.TypeException;
import model.exceptions.FileException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.types.StringType;
import model.types.Type;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//varName = "test.in"
//OpenRFile(varName)
public class OpenRFile implements IStmt {
    private IExpression fileName;   //new VarExpr(new StringV)

    public OpenRFile(IExpression fileName) {
        this.fileName = fileName;
    }

    /**
     * Checks if the file already exists in the FileTable before adding it.
     * It must have a unique file name.
     *
     * @param prg - program state
     * @return true if already exists ->error
     * false if its an open spot
     */
    private boolean isOpen(PrgState prg, StringValue fN) {
        for (StringValue crt : prg.getFileTable().getAll())
            if (crt.getVal().equals(fN.getVal()))
                return true;
        return false;
    }

    @Override
    public PrgState execute(PrgState state) {
        try {
            Value val = fileName.eval(state.getSymbolT(), state.getHeap());

            //Check if the expression in the statement is of StringType
            if (!val.getType().equals(new StringType()))
                throw new TypeException("OpenRFile has no StringType argument!");

            StringValue stringFileName = (StringValue) val;

            //Check if it exists in the file table to NOT insert duplicate
            if (isOpen(state, stringFileName))
                throw new FileException("File name already exists!");

            BufferedReader br = new BufferedReader(new FileReader(stringFileName.getVal()));
            state.getFileTable().add(stringFileName, br);

        } catch (IOException e) {
            // If the file does not exist or other IO error occurs the execution
            // is stopped with an appropriate error message.
            throw new FileException(e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "openFile(" + fileName + ")";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typexp = fileName.typecheck(typeEnv);
        if (typexp.equals(new StringType()))
            return typeEnv;
        else
            throw new TypeException("CloseRFile: expression is not type string.");
    }
}
