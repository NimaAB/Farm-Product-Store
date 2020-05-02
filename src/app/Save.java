package app;

import filehandling.csv.SaveCSV;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.layout.BorderPane;
import validations.Alerts;
import validations.ioExceptions.InvalidFileNameException;

import javax.swing.*;

/**
 * En generisk klasse som bruker metodene fra SaveCSV.
 * og har en dialog metodet.
 * Klassen aktiver lagrings tråden også.
 * metodene i denne klassen er brukt AdminController og CustomerController.
 * */
public class Save<T> {
    private final BorderPane currentPane;
    private final SaveCSV<T> saveCSV;

    public Save(BorderPane currentPane, SaveCSV<T> saveCSV){
        this.currentPane=currentPane;
        this.saveCSV = saveCSV;
    }

    public static String pathDialog(String filePath) throws InvalidFileNameException {
        String melding = "filen din blir lagert i denne plaseringen: "+filePath +
                "\nGi filen din et navn: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 = pathStr + ".csv";
        if (pathStr.isEmpty() || pathStr.contains(".")||pathStr.contains(",")||
                pathStr.contains(";")||pathStr.contains("!") || pathStr.contains("?")){
            throw new InvalidFileNameException("Fil navn kan ikke være tom eller inneholde \".,;!?\"");
        }
        return filePath+ "\\" + pathStr1;
    }

    public void saveFile(){
        saveCSV.setOnSucceeded(this::writingDone);
        saveCSV.setOnFailed(this::writingFailed);
        Thread thread = new Thread(saveCSV);
        currentPane.setDisable(true);
        thread.setDaemon(true);
        thread.start();
    }

    private void writingDone(WorkerStateEvent e){
        saveCSV.call();
        currentPane.setDisable(false);
    }

    private void writingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        Alerts.warning("Thread Failed: "+e.getMessage());
        currentPane.setDisable(false);
    }
}
