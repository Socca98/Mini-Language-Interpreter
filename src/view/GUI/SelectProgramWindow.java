package view.GUI;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.GUI.controllers.SelectProgramController;

import java.io.IOException;
import java.util.List;

public class SelectProgramWindow {

    /**
     * Function that creates a select program modal. It is called in MainController in the function that handles click
     * event for Select Menu Item.
     * @param programs list of Controller, each with one PrgState in the Repository
     * @return Controller with the selected PrgState from the list view of SelectProgramWindow
     * @throws IOException - idk
     */
    public Controller display(List<Controller> programs) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/select_program_view.fxml"));
        Parent selectProgramWindow = loader.load();
        SelectProgramController controller = loader.getController();
        controller.setPrograms(programs);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select Program");
        stage.getIcons().add(new Image("/assets/selectIcon.png"));

        Scene scene = new Scene(selectProgramWindow, 500, 400 );

        stage.setScene(scene);
        stage.showAndWait();

        return controller.getSelectedProgram();
    }
}
