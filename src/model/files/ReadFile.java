package model.files;

import model.statements.IStmt;
import model.exceptions.TypeException;
import model.exceptions.FileException;
import model.exceptions.InexistentSymbolException;
import model.expressions.IExpression;
import model.program.PrgState;
import model.types.IntType;
import model.types.StringType;
import model.containers.IDictionary;
import model.types.Type;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class ReadFile implements IStmt {
    private IExpression expr; //when its expression in ReadFile(2+3, varName)
    private String varName;

    public ReadFile(IExpression expr, String varName) {
        this.expr = expr;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) {
        IDictionary<String, Value> dict = state.getSymbolT();
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();

        try {
            //Check if varName is defined in symbolTable and is IntegerType
            if (!dict.contains(varName) || !dict.get(varName).getType().equals(new IntType()))
                throw new InexistentSymbolException("In ReadFile, this variable does not exist or its not integer type!");

            Value valueOfExpression = expr.eval(dict, state.getHeap());

            //Check if expr is StringType
            if (!valueOfExpression.getType().equals(new StringType()))
                throw new TypeException("The file name variable is not StringType in ReadFile!");

            StringValue stringFileName = (StringValue) valueOfExpression;

            //Check if file exists in fileTable
            if (!fileTable.contains(stringFileName))
                throw new FileException("In ReadFile, the StringValue does not exist in File Table!");

            String line = fileTable.get(stringFileName).readLine();

            //If line is empty, we assign default(0) to the variable
            if (Objects.equals(line, "") || Objects.equals(line, null)) {
                IntType defaultIntType = new IntType();
                dict.add(varName, defaultIntType.defaultValue());
            } else {
                int intValue = Integer.parseInt(line);
                IntValue intValueFile = new IntValue(intValue);
                dict.add(varName, intValueFile);
            }
            return null;

        } catch (IOException e) {
            throw new FileException("Can't read line from file!");
        }
    }

    @Override
    public String toString() {
        return "readFile(" + expr + ", " + varName + ")";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        Type typexp = expr.typecheck(typeEnv);
        Type typevar = typeEnv.get(varName);
        if (typexp.equals(new StringType()))
            if (typevar.equals(new IntType()))
                return typeEnv;
            else throw new TypeException("ReadFile: variable is not type integer.");
        else
            throw new TypeException("ReadFile: expression is not type string.");
    }
}
