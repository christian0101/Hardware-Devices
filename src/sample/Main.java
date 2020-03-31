package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Thread m_MainThread;
    private Boolean stop = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Recovery Application");
        primaryStage.setScene(new Scene(root, 500, 275));
        primaryStage.show();

        Runnable r = new Runnable() {
            public void run() {
                while (!stop) {
                    DigitalOutput digitalOutput = new DigitalOutput();
                    try {
                        if (digitalOutput.initialise()) {
                            digitalOutput.makeSound(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        m_MainThread = new Thread(r);
        m_MainThread.start();
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    @Override
    public void stop(){
        setStop(true);
    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
