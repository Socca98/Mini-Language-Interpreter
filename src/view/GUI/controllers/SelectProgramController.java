package view.GUI.controllers;


import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.containers.Dictionary;
import model.containers.IDictionary;
import model.exceptions.TypeException;
import model.types.Type;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SelectProgramController implements Initializable {
    @FXML
    private ListView<String> programsView;
    @FXML
    private Button cancelButton;
    @FXML
    private Button selectButton;

    private Controller selectedProgram;

    private List<Controller> programs;

    public SelectProgramController() {
        programs = new ArrayList<>();
        selectButton = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void selectButtonAction() {
        int index = programsView.getSelectionModel().getSelectedIndex();
        selectedProgram = programs.get(index);
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setPrograms(List<Controller> programs) {
        this.programs = programs;
        programsView.setItems(FXCollections.observableArrayList(programs.stream().map(Controller::originalStatementToString).collect(Collectors.toList())));
    }

    public Controller getSelectedProgram() {
        return selectedProgram;
    }

//    public void checkTypeValidity() throws TypeException {
//        this.selectedProgram.getFirstProgram().getExeStack().top().typecheck(new Dictionary<>());
//    }
}