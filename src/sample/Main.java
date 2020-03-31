package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller sceneController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        sceneController = loader.getController();
        sceneController.setMain(this);

        primaryStage.setTitle("Recovery Application");
        primaryStage.setScene(new Scene(root, 500, 330));
        primaryStage.show();
    }

    public void printMsg(String msg) {
        sceneController.updateData(msg);
    }

    public static void main(String[] args) throws Exception {
        launch(args);

        // quit all threads
        System.exit(0);
    }
}
