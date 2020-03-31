package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Main m_MainRef;
    @FXML
    Label welcomeLabel;

    @FXML
    ListView listView;

    @FXML
    Label dataLabel;

    public void initialize(URL arg0, ResourceBundle arg1) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        welcomeLabel.setText("Welcome, today is: " + dtf.format(now));

    }

    public void setMain(Main InMainRef) {
        m_MainRef = InMainRef;
    }

    @FXML
    public void updateData(String newVal) {
        Platform.runLater(() ->
        {
            dataLabel.setText(newVal);
        });
    }

    @FXML
    private void startActivity(ActionEvent event) throws InterruptedException {
        DigitalOutput digitalOutput = new DigitalOutput(m_MainRef);


        Runnable r = new Runnable() {
            public void run() {
                boolean doState = false;

                try {
                    Platform.runLater(() ->
                    {
                        dataLabel.setText("Exercise will start in 5 seconds.");
                    });
                    doState = digitalOutput.initialise();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread doThread = new Thread(r);
        doThread.start();
        doThread.sleep(5000);
        //dataLabel.setText("Current Data will be shown here: ");
    }
}
