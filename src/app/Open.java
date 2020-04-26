package app;

import dataModels.data.ConfigurationItems;
import dataModels.dataCollection.ListViewCollection;
import filehandling.csv.OpenCSV;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.layout.BorderPane;
import validations.Alerts;
import validations.customExceptions.InvalidFileException;

import java.util.ArrayList;

public class Open<T> {
    private final BorderPane currentPane;
    private OpenCSV <T> openCSV;

    public Open(BorderPane pane, OpenCSV<T> openCSV) {
        this.currentPane = pane;
        this.openCSV = openCSV;
    }
    public void openFile() {
        openCSV.setOnSucceeded(this::readingDone);
        openCSV.setOnFailed(this::readingFailed);
        Thread thread = new Thread(openCSV);
        currentPane.setDisable(true);
        thread.setDaemon(true);
        thread.start();
    }


    private void readingDone(WorkerStateEvent e) {

        try {
            ArrayList<T> itemsFromFile = openCSV.call();
            if()
            /*ListViewCollection.loadingConfig(configFromFile);
            ListViewCollection.showTotalPrice(totalPriceLbl);
            ListViewCollection.setModified(false);*/
        } catch (InvalidFileException exception){
            Alerts.warning(exception.getMessage());
            exception.printStackTrace();
        }

        currentPane.setDisable(false);
    }
    private void readingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        Alerts.warning("Thread Failed: " + e.getMessage());
        e.printStackTrace();
        currentPane.setDisable(false);
    }

}
