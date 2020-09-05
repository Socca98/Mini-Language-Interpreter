package repository;

import model.exceptions.FileException;
import model.program.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> myList = new ArrayList<>();
    private String logFilePath; //folder path to the log text file
    //initialized by keyboard input (in theory)

    public Repository(PrgState prg, String logFilePath) {
        this.myList.add(prg);
        this.logFilePath = logFilePath;
    }

    public List<PrgState> getPrgList() {
        return myList;
    }

    public void setPrgList(List<PrgState> myList) {
        this.myList = myList;
    }

    public void addPrgState(PrgState st) {
        this.myList.add(st);
    }

    public int getNumberOfPrgStates() {
        return this.myList.size();
    }

    public void removeCompleted() {
        myList.removeIf(e -> !e.isNotCompleted());
    }

    @Override
    public String toString() {
        return myList.get(0).getExeStack().top().toString();
    }

    /*
    Clears the content in the file indicated by the field logFilePath.
    IN:-
    OUT:-
    */
    public void clearLogFile() {
        try (PrintWriter writer = new PrintWriter(logFilePath)) {
            writer.print("");
        } catch (IOException e) {
            throw new FileException("IO exception at clearing with PrintWriter (in Repository)!");
        }
    }

    /*
    Writes to the file given by attribute 'logFilePath' the current state of the program given as parameter.
    IN: PrgState one Program State
    OUT:-
     */
    @Override
    public void logPrgStateExec(PrgState state) throws FileException {
        //Passing true for the second parameter indicates that you want to append to the file;
        //Passing false means that you want to overwrite the file.
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))) {
            logFile.print(state);
            logFile.print("\n");
        } catch (IOException e) {
            throw new FileException("IO exception at PrintWriter (in Repository)!");
        }
    }
}
