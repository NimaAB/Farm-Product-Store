package org.app.fileHandling;

import org.app.controllers.AdminController;
import org.app.data.dataCollection.CategoryCollection;
import org.app.data.dataCollection.TableViewCollection;
import org.app.data.models.Category;
import org.app.data.models.Product;
import org.app.fileHandling.fileThreads.OpenThread;
import org.app.fileHandling.fileThreads.SaveThread;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.app.validation.Alerts;
import org.app.validation.ioExceptions.InvalidTypeException;


import java.util.ArrayList;

public class IOClient<T> {

    private ArrayList<T> listToWrite;
    private FileInfo fileInfo;
    private OpenThread<T> openThread;
    private SaveThread<T> saveThread;
    private Alert loadingAlert;
    private TableViewCollection collection = TableViewCollection.getINSTANCE();
    private CategoryCollection categoryCollection = CategoryCollection.getInstance();

    public IOClient(FileInfo fileInfo, ArrayList<T> listToWrite) {
        this.fileInfo = fileInfo;
        this.listToWrite = listToWrite;
        this.saveThread = new SaveThread<>(this.fileInfo, this.listToWrite);
    }

    public IOClient(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        this.openThread = new OpenThread<>(this.fileInfo);
    }

    public void runSaveThread(String msg) {
        loadingAlert = Alerts.showLoadingDialog(saveThread, msg);
        saveThread.setOnSucceeded(this::saveDone);
        saveThread.setOnFailed(this::saveFailed);
        saveThread.setOnRunning((e) -> loadingAlert.show());
        Thread th = new Thread(saveThread);
        th.setDaemon(true);
        th.start();
    }

    private void saveDone(WorkerStateEvent e) {
        try {
            saveThread.call();
            loadingAlert.close();
            Alerts.success("Filen din ble lagret i: " + fileInfo.getFullPath());
        } catch (InvalidTypeException ignore) {}
    }

    private void saveFailed(WorkerStateEvent event) {
        Throwable e = event.getSource().getException();
        Alerts.warning(e.getMessage());
        loadingAlert.close();
    }

    public void runOpenThread(String msg) {
        loadingAlert = Alerts.showLoadingDialog(openThread, msg);
        openThread.setOnSucceeded(this::openDone);
        openThread.setOnFailed(this::openFailed);
        openThread.setOnRunning((e) -> loadingAlert.show());
        Thread th = new Thread(openThread);
        th.setDaemon(true);
        th.start();
    }

    @SuppressWarnings("unchecked")
    private void openDone(WorkerStateEvent e) {
        try {
            if(!openThread.getValue().isEmpty()) {
                if(openThread.getValue().get(0) instanceof Category) {
                    categoryCollection.getCategoryObjects().clear();
                    for(T c: openThread.getValue()) categoryCollection.addCategory((Category) c);
                } else {
                    ArrayList<Product> list = (ArrayList<Product>) openThread.call();
                    collection.getComponents().clear();
                    collection.setComponents(list);
                    collection.setLoadedFile(fileInfo.getFullPath());
                    collection.setModified(false);
                    AdminController.filenameLabelStatic.setText(fileInfo.getFileName());
                }
            }
            loadingAlert.close();
        } catch (InvalidTypeException ignored) {
        }
    }

    private void openFailed(WorkerStateEvent event) {
        Throwable e = event.getSource().getException();
        Alerts.warning(e.getMessage());
        loadingAlert.close();
    }

}
