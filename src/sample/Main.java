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

        DigitalOutput digitalOutput = new DigitalOutput(this);
        Runnable r = new Runnable() {
            public void run() {
                boolean doState = false;

                try {
                    doState = digitalOutput.initialise();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                try {
//                    if (digitalOutput.initialise()) {
//                        digitalOutput.makeSound(0);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        };

        Thread doThread = new Thread(r);
        doThread.start();

        //doThread.interrupt();
    }

    public void printMsg(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) throws Exception {
        launch(args);

        // quit all threads
        System.exit(0);
    }
}
