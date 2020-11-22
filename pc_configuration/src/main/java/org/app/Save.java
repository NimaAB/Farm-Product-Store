package org.app;

import filehandling.bin.SaveBin;
import filehandling.csv.SaveCSV;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import org.app.controllers.DialogWindowC;
import validations.Alerts;
import validations.ioExceptions.InvalidExtensionException;
import validations.ioExceptions.InvalidFileNameException;

import java.io.IOException;
//import javax.swing.*;

/**
 * En generisk klasse som bruker metodene fra SaveCSV.
 * og har en dialog metode.
 * Klassen aktiverer lagrings tråden også.
 * metodene i denne klassen er brukt AdminController og CustomerController.
 * */

public class Save<T> {
    private final BorderPane currentPane;
    private final SaveCSV<T> saveCSV;
    private final SaveBin<T> saveBin;

    public Save(BorderPane currentPane, SaveCSV<T> saveCSV,SaveBin<T> saveBin){
        this.currentPane=currentPane;
        this.saveCSV = saveCSV;
        this.saveBin = saveBin;
    }

    public static String pathDialog(String filePath) throws InvalidFileNameException {
        String melding = "filen din blir lagert i denne plaseringen: "+filePath +
                "\nGi filen din et navn: ";
        String pathStr = "";
        try {
            WindowLoader wl = new WindowLoader("dialogBox");
            wl.loadNewWindow();
            DialogWindowC dw = new DialogWindowC();
            dw.setInfo(melding);
            pathStr = dw.getPath();

        }catch (IOException e){
            e.getStackTrace();
        }

        if (pathStr.isEmpty()){
            throw new InvalidFileNameException("Fil navn kan ikke være tom.");
        }else{
            return filePath+"\\"+ pathStr;
        }
    }

    public static String extension(String path){
        boolean containsBin = path.contains(".bin");
        boolean containsCsv = path.contains(".csv");
        if(containsBin ^ containsCsv){
            return path.substring(path.lastIndexOf("."));
        }else{
            throw new InvalidExtensionException("Skriv fil typen - \"filename.csv\"");
        }
    }

    public void saveFile(){
        Thread thread;
        if(saveCSV != null){
            saveCSV.setOnSucceeded(this::writingCSVDone);
            saveCSV.setOnFailed(this::writingFailed);
            thread= new Thread(saveCSV);
        }
        else{
            saveBin.setOnSucceeded(this::writingBinDone);
            saveBin.setOnFailed(this::writingFailed);
            thread = new Thread(saveBin);
        }
        currentPane.setDisable(true);
        thread.setDaemon(true);
        thread.start();
    }

    private void writingCSVDone(WorkerStateEvent e){
        saveCSV.call();
        currentPane.setDisable(false);
    }
    private void writingBinDone(WorkerStateEvent e ){
        saveBin.call();
        currentPane.setDisable(false);
    }

    private void writingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        Alerts.warning("Thread Failed: "+e.getMessage());
        currentPane.setDisable(false);
    }
}
