package io;

import dataModels.dataCollection.TableViewCollection;
import dataModels.models.Product;
import io.fileThreads.OpenThread;
import io.fileThreads.SaveThread;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import validations.Alerts;
import validations.ioExceptions.InvalidTypeException;


import java.util.ArrayList;

public class IOClient<T> {

    private ArrayList<T> listToWrite;
    private FileInfo fileInfo;
    private OpenThread<T> openThread;
    private SaveThread<T> saveThread;
    private Alert loadingAlert;

    public IOClient(FileInfo fileInfo, ArrayList<T> listToWrite) {
        this.fileInfo = fileInfo;
        this.listToWrite = listToWrite;
        this.saveThread = new SaveThread<>(this.fileInfo, this.listToWrite);
    }

    public IOClient(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        this.openThread = new OpenThread<>(this.fileInfo);
    }

    public void runSaveThread() {
        loadingAlert = Alerts.showLoadingDialog(saveThread, "Saving file...");
        saveThread.setOnSucceeded(this::saveDone);
        saveThread.setOnFailed(this::saveFailed);
        saveThread.setOnRunning((e) -> loadingAlert.show());
        Thread th = new Thread(saveThread);
        th.setDaemon(true);
        th.start();
    }

    private void saveDone(WorkerStateEvent e) {
        saveThread.call();
        Alerts.success("Filen din ble lagret i: " + fileInfo.getPath());
        loadingAlert.close();
    }

    private void saveFailed(WorkerStateEvent event) {
        Throwable e = event.getSource().getException();
        Alerts.warning(e.getMessage());
        loadingAlert.close();
    }

    public void runOpenThread() {
        loadingAlert = Alerts.showLoadingDialog(openThread, "Opening file...");
        openThread.setOnSucceeded(this::openDone);
        openThread.setOnFailed(this::openFailed);
        openThread.setOnRunning((e) -> loadingAlert.show());
        Thread th = new Thread(openThread);
        th.setDaemon(true);
        th.start();
    }

    private void openDone(WorkerStateEvent e){
        try {
            TableViewCollection collection = TableViewCollection.getINSTANCE();
            ArrayList<Product> list = (ArrayList<Product>) openThread.call();
            collection.getComponents().clear();
            collection.setComponents(list);
            loadingAlert.close();
        }catch (InvalidTypeException exception){
            Alerts.warning(exception.getMessage());
        }
    }

    private void openFailed(WorkerStateEvent event) {
        Throwable e = event.getSource().getException();
        Alerts.warning(e.getMessage());
        loadingAlert.close();
    }

}
