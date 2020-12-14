package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainUI.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setTitle("RPGGameExercise");
        primaryStage.setScene(new Scene(root, 735, 690));
        primaryStage.show();
    }
}