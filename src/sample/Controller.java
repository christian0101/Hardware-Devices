package sample;

import com.phidgets.PhidgetException;
import com.sun.xml.internal.ws.db.DatabindingImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    private Main m_MainRef;
    @FXML
    Label welcomeLabel;

    @FXML
    ListView listView;

    @FXML
    Label dataLabel;

    @FXML
    Button startEx;

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
            dataLabel.setText("Pressure applied: " + newVal);
        });
    }

    @FXML
    private void startActivity(ActionEvent event) throws InterruptedException {
        DigitalOutput digitalOutput = new DigitalOutput(m_MainRef);

//        Runnable r = new Runnable() {
//            public void run() {
//                boolean doState = false;
//
//                try {
//                    doState = digitalOutput.initialise();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };

        digitalOutput.start();

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = 5;
            public void run() {
                if (i > 0) {
                    Platform.runLater(() ->
                    {
                        startEx.setText("Exercise will end in " + i-- + " seconds.");
                    });
                } else {
                    timer.cancel();

                    Platform.runLater(() ->
                    {
                        startEx.setText("Exercise completed! New reading?");
                    });

                    try {
                        digitalOutput.stopDO();
                        digitalOutput.interrupt();
                    } catch (PhidgetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 1000);
    }
}
