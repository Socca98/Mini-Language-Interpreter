package view.GUI.controllers;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import utils.ProgramGenerator;
import view.GUI.SelectProgramWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public TextField prgStatesNr;
    @FXML
    public TableView<Pair<String, String>> symTableView;
    @FXML
    public TableView<Pair<Integer, String>> heapView;
    @FXML
    public TableView<Pair<Integer, Integer>> lockView;
    @FXML
    public ListView<String> fileListView;
    @FXML
    public ListView<String> outputView;
    @FXML
    public ListView<String> programView;
    @FXML
    public ListView<Integer> threadView;
    @FXML
    public ListView<String> stackView;
    @FXML
    public TableColumn<Pair<String, String>, String> symVarColumn;
    @FXML
    public TableColumn<Pair<String, String>, String> symValueColumn;
    @FXML
    public TableColumn<Pair<Integer, Integer>, Integer> heapAddrColumn;
    @FXML
    public TableColumn<Pair<Integer, Integer>, String> heapValueColumn;
    @FXML
    public TableColumn<Pair<Integer, Integer>, Integer> lockLocationColumn;
    @FXML
    public TableColumn<Pair<Integer, Integer>, Integer> lockValueColumn;

    public MenuItem selectProgramWindow;
    public Button runButton;
    private List<Controller> toyProgramControllers = new ArrayList<>();
    private Controller currentController;   //current program that is being run, when another is selected, everything is reset
    private int currentThread;

    public MainController() {
        try {
            toyProgramControllers = ProgramGenerator.generatePrograms();    //will throw if types are not correct
            currentController = toyProgramControllers.get(0);
            currentThread = currentController.getAnyThreadId();
            //^ by default, the id would be 0, but we have no threads with id=0. I could give it value '1', but lets give it
            // the first id we find from the first thread using getAnyThreadId()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //https://docs.oracle.com/javafx/2/api/javafx/scene/control/cell/PropertyValueFactory.html
            //key, value must be fields of the object Pair<String, String>
            symVarColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
            symValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

            heapAddrColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
            heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

            lockLocationColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
            lockValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

            threadView.getSelectionModel().select(0);
            updateGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when 'Run one step' button is clicked. It executes one statement from the execution stack
     * for all threads. Then refreshes the GUI list views.
     */
    public void runOneStep() {
        try {
            currentController.conservativeGarbageCollector(currentController.getPrograms());
            currentController.oneStepForAllPrg(currentController.getPrograms());
//            currentController.getRepo().setPrgList(currentController.removeCompletedPrg(currentController.getPrograms()));
//            currentThread = currentController.getAnyThreadId();
            updateGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateGUI() {
        prgStatesNr.setText(currentController.getSizeOfRepository());
        programView.setItems(FXCollections.observableArrayList(currentController.getThread(currentThread).originalStatementToString().split("\n")));
        stackView.setItems(FXCollections.observableArrayList(currentController.getExeStack(currentThread)));
        symTableView.setItems(FXCollections.observableArrayList(currentController.getSymTable(currentThread)));
        heapView.setItems(FXCollections.observableArrayList(currentController.getHeap()));
        fileListView.setItems(FXCollections.observableArrayList(currentController.getFileTable()));
        outputView.setItems(FXCollections.observableArrayList(currentController.getOutput().split("\n")));
        threadView.setItems(FXCollections.observableArrayList(currentController.getThreadIds()));
        lockView.setItems(FXCollections.observableArrayList(currentController.getLockTable()));
    }

    /**
     * Called when you click something in the thread view (bottom right).
     */
    public void selectThread() {
        try {
            currentThread = threadView.getSelectionModel().getSelectedItems().get(0);
            updateGUI();
        } catch (Exception ignored) {
        }
    }

    /**
     * Function called when 'Select program' is clicked from Menu Bar.
     * If another program is selected then all active threads are deleted.
     *
     * @throws IOException idk
     */
    public void selectProgram() throws IOException {
        SelectProgramWindow selectProgramWindow = new SelectProgramWindow();
        Controller selectedProgram = selectProgramWindow.display(toyProgramControllers); //returns the selected program
        if (selectedProgram != null) {
            currentController = selectedProgram;
            currentThread = currentController.getAnyThreadId();
            updateGUI();
        }
    }
}