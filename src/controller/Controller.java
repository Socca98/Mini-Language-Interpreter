package controller;

import javafx.util.Pair;
import model.containers.IList;
import model.statements.IStmt;
import model.containers.IExeStack;
import model.program.PrgState;
import model.containers.IHeap;
import model.values.RefValue;
import model.values.StringValue;
import model.values.Value;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private boolean displayFlag = true;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
        executor = Executors.newFixedThreadPool(2);
        repo.clearLogFile();    //empty file before writing to it
    }

    /**
     * Create a new Heap with entries that have the key(an integer) present in the set 'references'
     * @param references a set with addresses that reference an 'alive' object
     * @param heap       the actual heap of the PrgStates
     * @return a new Heap with less or equal number of entries
     */
    private Map<Integer, Value> cleanup(Set<Integer> references, IHeap<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> references.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    /**
     * Collect all addresses from RefValues from Symbol Table and Heap.
     * Then if those addresses exist as Keys in the Heap they must be preserved (not garbaged, what a word)
     * @param programs list with all the programs
     */
    public void conservativeGarbageCollector(List<PrgState> programs) {
        Set<Integer> mergedReferences = new HashSet<>();

        // Go through each symbol table of each Program State and
        // get the addresses from the variables of type RefValues
        // similar to function getAddrFromSymTable(..)
        for (PrgState p : programs) {
            Set<Integer> referencesSymTable = p.getSymbolT().getAllValues().stream()
                    .filter(v -> v instanceof RefValue)
                    .map(v -> {
                        RefValue v1 = (RefValue) v;
                        return v1.getAddr();
                    }).collect(Collectors.toSet());

            mergedReferences.addAll(referencesSymTable);
        }

        // getAddrFromHeap(..)
        Set<Integer> referencesHeap = programs.get(0).getHeap().getValueSet().stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                }).collect(Collectors.toSet());
        mergedReferences.addAll(referencesHeap);

        //Get the new Heap which was garbage collected and assign it to the Heap that each
        //program has assigned. They share the same Heap, so we must do only one assignment/setContent()
        programs.get(0).getHeap().setContent(cleanup(mergedReferences, programs.get(0).getHeap()));
    }

    /**
     * Gets a list of Program States, removes those which have execution stack empty aka are completed then
     * returns a list with all the Program States which are not finished.
     * @param inPrgList List<PrgState>
     * @return List<PrgState>
     */
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    /**
     * Executes one step for each existing PrgState (namely each thread).
     * @param prgList List of Program States
     */
    public void oneStepForAllPrg(List<PrgState> prgList) {
        // Writes to the same text file, the state of each program. We must write ids so we differentiate them.
        prgList.forEach(prg -> repo.logPrgStateExec(prg));

        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables
        //Have a list where each element calls the method oneStep of each Program State
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        try {
            //start the execution of the callables
            //it returns the list of new created PrgStates (namely threads)
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future ->
                    {
                        try {
                            return future.get(); //runs the oneStep method
                        } catch (Exception e) {
                            //here we catch "Stack is empty!" but we do nothing
                            return null;
                        }
                    })
                    .filter(Objects::nonNull) //keeps only the Program without errors at running oneStep
                    .collect(Collectors.toList());

            //add the new created threads to the list of existing threads
            prgList.addAll(newPrgList);

            //after the execution, print the PrgState List into the log file
            prgList.forEach(prg -> repo.logPrgStateExec(prg));

            //Save the current programs in the repository
            repo.setPrgList(prgList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs oneStep for each Program State until each is completed and has no more statements to be executed.
     */
    public void allStep() {
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList()); //remove the completed programs

        while (prgList.size() > 0) {
            //Call the Garbage Collector
            conservativeGarbageCollector(prgList);
            //Call OneStep for each PrgState
            oneStepForAllPrg(prgList);
            //Remove the completed programs
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();


        // HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository

        // update the repository state
        repo.setPrgList(prgList);
    }

    public IRepository getRepo() {
        return repo;
    }

    public List<PrgState> getPrograms() {
        return repo.getPrgList();
    }

    public String getSizeOfRepository() {
        return String.valueOf(repo.getNumberOfPrgStates());
    }

    public String originalStatementToString() {
        return repo.getPrgList().get(0).originalStatementToString();
    }

    @Override
    public String toString() {
        return repo.getPrgList().get(0).getExeStack().top().toString();
    }

    /**
     * We get first program in order to use its Heap, FileTable, etc. Cause
     * these are shared structures and we don't need to waste time to search for the correct thread.
     *
     * @return the first PrgState from the Repository list
     */
    public PrgState getFirstProgram() {
        return repo.getPrgList().get(0);
    }

    /**
     * Get the entries inside the heap of the first PrgState.
     * 2 --> 20
     * 3 --> (2, int)
     * 4 --> 30
     *
     * @return list with entries
     */
    public List<Pair<Integer, String>> getHeap() {
        List<Pair<Integer, String>> result = new ArrayList<>();
        PrgState thread = getFirstProgram();    //Heap is shared
        for (Integer key : thread.getHeap().getKeySet())
            result.add(new Pair<>(key, thread.getHeap().get(key).toString()));
        return result;
    }

    /**
     * The file table as a list view.
     * "test.in" --> (java.io.BufferedReader@4ded9806)
     *
     * @return list of file names
     */
    public List<String> getFileTable() {
        List<String> result = new ArrayList<>();
        PrgState thread = getFirstProgram();    //FileTable is shared
        thread.getFileTable().getAll().forEach(key -> result.add(key.toString()));
        return result;
    }

    /**
     * Output table as a list view.
     * Output table string representation taken from any PrgState(lets choose the first) cause its shared structure.
     * Alt + enter made me transform it into a StringBuilder
     * [99, 7, 88, 2, 0]
     * @return string representation of a list
     */
    public String getOutput() {
        StringBuilder result = new StringBuilder();
        for (Value v : getFirstProgram().getOutputList().getAll())
            result.append(v.toString()).append("\n");

        return result.toString();
    }

    /**
     * Get the first id we find while iterating the threads.
     * @return id of first thread found
     */
    public Integer getAnyThreadId() {
        return repo.getPrgList().stream().map(PrgState::getId).findFirst().get();
    }

    /**
     * Return the PrgState that has the id equal to the parameter.
     * So we display the correct SymbolTable, ExeStack, etc. (the individual structures)
     * @param threadID a parameter to find the correct PrgState/Thread
     * @return the wanted PrgState/Thread
     */
    public PrgState getThread(int threadID) {
        return repo.getPrgList().stream().filter(e -> e.getId() == threadID).findFirst().get();
        //it does not verify if its null (~not found), thats why we should use Optional somehow
    }

    /**
     * Get all the statements from the exe stack.
     * @param currentThread id of thread
     * @return list of statement
     */
    public List<String> getExeStack(int currentThread) {
        IExeStack<IStmt> stack = getThread(currentThread).getExeStack();
        List<String> result = new ArrayList<>();
        stack.getAll().forEach(iStmt -> result.add(iStmt.toString()));
        return result;
    }

    /**
     * Get a string representation of the sym table that we can use in our GUI.
     * a = (3, Ref(int))
     * v = (2, int)
     * b = 10
     * var_f = "test.in"
     * @param currentThread which symbol table do you need?
     * @return list of pair objects
     */
    public List<Pair<String, String>> getSymTable(int currentThread) {
        List<Pair<String, String>> result = new ArrayList<>();
        PrgState thread = getThread(currentThread);
        for (String key : thread.getSymbolT().getAll())
            result.add(new Pair<>(key, thread.getSymbolT().get(key).toString()));
        return result;
    }

    /**
     * Get all the ids of all the threads, so we can display them in a list view.
     * @return integers representing ids of threads
     */
    public List<Integer> getThreadIds() {
        List<Integer> result = new ArrayList<>();
        for (PrgState p : repo.getPrgList())
            result.add(p.getId());

        return result;
    }

    /**
     * Get everything from Lock Table.
     * @return list for gui
     */
    public List<Pair<Integer, Integer>> getLockTable() {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        PrgState thread = getFirstProgram();

        for (Integer key : thread.getLockTable().keySet()) {
            result.add(new Pair<>(key, thread.getLockTable().get(key)));
        }
        return result;
    }
}


//In case of emergency at exam. Copy this functionality and edit it :<

// Close all existing files from the FileTable shared by all
//        List<PrgState> tmpList= repo.getPrgList();
//        tmpList.get(0).getFileTable().getValues().
//                forEach(e ->
//                {
//                    try {
//                        e.getReader().close();
//                    } catch(IOException ex) {
//                        System.out.println(ex.getMessage());
//                    }
//                });

