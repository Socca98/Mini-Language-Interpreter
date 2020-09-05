package repository;

import model.program.PrgState;

import java.util.List;

public interface IRepository {
    /*
    Adds a new Program State in the collection.
    One PrgState contains ExeStack,OutputList,SymTable
    IN: PrgState
    OUT: -
    */
    void addPrgState(PrgState st);

    /*
    Clears the content in the file indicated by the field logFilePath.
    IN:-
    OUT:-
     */
    void clearLogFile();

    /*
    Saves the parameter ProgramState into a file.
    IN:-
    OUT:-
     */
    void logPrgStateExec(PrgState state);

    /*
    Returns the list containing all program states.
    IN: -
    OUT: List<PrgState>
     */
    List<PrgState> getPrgList();

    /*
    Assigns to the list of program states another one from the parameters.
    IN: List<PrgState>
    OUT: -
     */
    void setPrgList(List<PrgState> list);

    /**
     * Returns an integer representing how many PrgStates we have in the repository list.
     * @return integer
     */
    int getNumberOfPrgStates();

    void removeCompleted();
}
