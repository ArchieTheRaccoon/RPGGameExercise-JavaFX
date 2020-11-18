package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainUI.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();
        controller.setController(controller);

        primaryStage.setTitle("RPGGameExercise");
        primaryStage.setScene(new Scene(root, 735, 690));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
