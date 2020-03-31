package sample;

import com.phidgets.PhidgetException;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

        UpdateListView();
    }

    @FXML
    private void UpdateListView() {
        Platform.runLater(() ->
        {
            List<String> lines = null;
            try {
                lines = Files.readAllLines(Paths.get("output.txt"));
                ObservableList<String> observableList = FXCollections.observableList(lines);
                listView.setItems(observableList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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

    private void saveFile(int InResult) throws IOException {
        File file = new File("output.txt");
        System.out.println("Saving to file");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            fr.append(dtf.format(now));
            fr.append(' ');
            fr.append(Integer.toString(InResult));
            fr.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void startActivity(ActionEvent event) throws InterruptedException {
        DigitalOutput digitalOutput = new DigitalOutput(m_MainRef);
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
                        saveFile(digitalOutput.getResult());
                        UpdateListView();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        digitalOutput.makeSound(0, 1000000);
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
