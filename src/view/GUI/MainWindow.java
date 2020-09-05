package view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent mainWindow = FXMLLoader.load(getClass().getResource("views/main_window_view.fxml"));

        primaryStage.setTitle("Toy Language Interpreter");
        primaryStage.getIcons().add(new Image("file:mainIcon.png"));

        Scene scene = new Scene(mainWindow, 1000, 800 );

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
