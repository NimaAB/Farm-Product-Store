package io;

import dataModels.dataCollection.TableViewCollection;
import dataModels.models.Product;
import io.fileThreads.OpenThread;
import io.fileThreads.SaveThread;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Label;
import validations.Alerts;
import validations.ioExceptions.InvalidTypeException;


import java.util.ArrayList;

public class IOClient<T> {

    private ArrayList<T> listToWrite;
    private FileInfo fileInfo;
    private OpenThread<T> openThread;
    private SaveThread<T> saveThread;

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
        saveThread.setOnSucceeded(this::saveDone);
        saveThread.setOnFailed(this::saveFailed);
        Thread th = new Thread(saveThread);
        th.setDaemon(true);
        th.start();
    }

    private void saveDone(WorkerStateEvent e) {
        saveThread.call();
    }

    private void saveFailed(WorkerStateEvent event) {
        Throwable e = event.getSource().getException();
        Alerts.warning(e.getMessage());
    }

    public void runOpenThread() {
        openThread.setOnSucceeded(this::openDone);
        openThread.setOnFailed(this::openFailed);
        Thread th = new Thread(openThread);
        th.setDaemon(true);
        th.start();
    }

    private void openDone(WorkerStateEvent e) {
        TableViewCollection collection = TableViewCollection.getINSTANCE();
        try {
            ArrayList<Product> list = (ArrayList<Product>) openThread.call();
            collection.getComponents().clear();
            collection.setComponents(list);
        }catch (InvalidTypeException exception){
            Alerts.warning(exception.getMessage());
        }
    }

    private void openFailed(WorkerStateEvent event) {
        Throwable e = event.getSource().getException();
        Alerts.warning(e.getMessage());
    }

}
