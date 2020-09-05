package model.statements;

import model.containers.IDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.TypeException;
import model.exceptions.InexistentSymbolException;
import model.program.PrgState;
import model.types.Type;
import model.values.Value;

// new VarDeclStmt("a", new IntType())
public class VarDeclStmt implements IStmt {
    private String name;
    private Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws DivisionByZeroException, InexistentSymbolException, TypeException {
        Value newVariable;

        //default constructors are called
//        if(type.equals(new IntType()))
//            newVariable = new IntValue();   // = 0
//        if(type.equals(new BoolType()))
//            newVariable = new BoolValue();  // = false
        newVariable = type.defaultValue();

        if (newVariable == null)
            throw new TypeException("Type of variable not implemented in class VarDecl!\n");

        state.getSymbolT().add(name, newVariable);
        return null;
    }

    @Override
    public String toString() {
        return type + " " + name + ";";
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnv) throws TypeException {
        IDictionary<String, Type> newEnv = typeEnv.deepCopy();
        newEnv.add(name, type);
        return newEnv;
    }
}


//VarDecl("varName", new IntType())