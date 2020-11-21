package org.app;

import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import dataModels.dataCollection.ListViewCollection;
import dataModels.dataCollection.TableViewCollection;
import filehandling.csv.OpenCSV;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import validations.Alerts;
import validations.ioExceptions.InvalidFileException;
import java.util.ArrayList;

/**
 * En generisk klasse som bruker metodene fra OpenCSV.
 * Klassen aktiverer lesings tråden.
 * metodene i denne klassen er brukt AdminController og CustomerController.
 * */

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
    @SuppressWarnings("unchecked")
    private void readingDone(WorkerStateEvent e) {
        currentPane.setDisable(false);
        try {
            ArrayList<T> itemsFromFile = openCSV.call();
            if(itemsFromFile.isEmpty()){
                Alerts.warning("filen er tom! Prøv en annen fil.");
            }else {
                boolean isComponent= itemsFromFile.get(0) instanceof Components;
                if (!isComponent && !User.isAdmin()) {
                    ListViewCollection.loadingConfig((ArrayList<ConfigurationItems>) itemsFromFile);
                    ListViewCollection.showTotalPrice(lbl);
                    ListViewCollection.setModified(false);
                } else if(isComponent && User.isAdmin()){
                    if(!TableViewCollection.getComponents().isEmpty()){
                        TableViewCollection.getComponents().clear();
                    }
                    TableViewCollection.setComponents((ArrayList<Components>) itemsFromFile,true);
                }else {
                    Alerts.warning("Feil Type: Programmet støtter ikke din data.");
                }
            }
        } catch (InvalidFileException exception){
            Alerts.warning(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void readingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        Alerts.warning("Thread Failed: " + e.getMessage());
        currentPane.setDisable(false);
    }
}
