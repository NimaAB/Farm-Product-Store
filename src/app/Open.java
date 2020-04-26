package app;

import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import dataModels.dataCollection.ListViewCollection;
import dataModels.dataCollection.TableViewCollection;
import filehandling.csv.OpenCSV;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import validations.Alerts;
import validations.customExceptions.InvalidFileException;

import java.util.ArrayList;

public class Open<T> {
    private final BorderPane currentPane;
    private final OpenCSV <T> openCSV;
    private final Label lbl;

    public Open(BorderPane pane, OpenCSV<T> openCSV,Label lbl) {
        this.currentPane = pane;
        this.openCSV = openCSV;
        this.lbl = lbl;
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
            if(itemsFromFile.isEmpty()){
                Alerts.warning("filen er tom! Prøv en annen fil.");
            }else {
                Object obj = itemsFromFile.get(0);
                if (obj instanceof ConfigurationItems && currentPane.getId().equals("customerPane")) {
                    ListViewCollection.loadingConfig((ArrayList<ConfigurationItems>) itemsFromFile);
                    ListViewCollection.showTotalPrice(lbl);
                    ListViewCollection.setModified(false);
                } else if (obj instanceof Components && currentPane.getId().equals("adminPane")) {
                    for (Components el : (ArrayList<Components>) itemsFromFile) {
                        TableViewCollection.addComponent(el);
                    }
                } else {
                    Alerts.warning("filen kan ikke lastes opp! Prøv en annen fil.");
                }
            }
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
