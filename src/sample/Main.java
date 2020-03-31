package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Recovery Application");
        primaryStage.setScene(new Scene(root, 500, 275));
        primaryStage.show();

        Runnable r = new Runnable() {
            public void run() {
                DigitalOutput digitalOutput = new DigitalOutput();
                try {
                    if (digitalOutput.initialise()) {
                        digitalOutput.makeSound(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread doT = new Thread(r);
        doT.start();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
