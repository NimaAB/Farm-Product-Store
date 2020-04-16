package app.controllers;

import app.Load;
import dataModels.data.Categories;
import dataModels.data.Components;
import dataModels.data.DataCollection;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import validations.MyAlerts;
import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private BorderPane adminPane;
    @FXML private TextField nr, name, category, price, txtFilter;
    @FXML private TextArea specifications;
    @FXML private ComboBox<String> optCategories;
    @FXML private ComboBox<String> optFilterBy;
    @FXML private TableView<Components> tableview;
    private final String BINPATH = "src/database/components.bin";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataCollection.loadComponents(BINPATH);
        DataCollection.setTableView(tableview);
        DataCollection.filterOnChange(optFilterBy);
        DataCollection.filterTableView(tableview,txtFilter);
        Categories.categoryOnChange(optCategories,category);
    }

    @FXML void opprettKomponent(){
        try {
            String nr = this.nr.getText();
            String name = this.name.getText();
            String category = this.category.getText();
            String specs = specifications.getText();
            String price = this.price.getText();
            CheckBox b = new CheckBox();

            Components component = new Components(nr,name,category,specs,price, b);
            DataCollection.addComponent(component);

            MyAlerts.successAlert("Component Created");
        } catch (IllegalArgumentException e) {
            MyAlerts.warningAlert(e.getMessage());
        }
    }

    @FXML void slett(){
        DataCollection.deleteSelectedComponents();
        tableview.refresh();
    }
    private OpenCSV<Components> openCSV;
    @FXML void open(){
        String melding = "last opp en fil til programmet fra denne plasering: src\\database\\lagringsPlass " +
                "\nSkriv navnet til fil du vil laste opp: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 = "src/database/lagringsPlass/";
        String totalPathStr =pathStr1+pathStr + ".csv";
        if(pathStr != null){
            openCSV = new OpenCSV<>(totalPathStr);
            openCSV.setOnSucceeded(this::readingDone);
            openCSV.setOnFailed(this::readingFailed);
            Thread th = new Thread(openCSV);
            th.setDaemon(true);
            adminPane.setDisable(true);
            th.start();
        }else{
            MyAlerts.warningAlert("Ingen fil er valgt");
        }
    }
    private void readingDone(WorkerStateEvent e){
        ArrayList<Components> componentsList = openCSV.call();
        for(Components el:componentsList){
            DataCollection.addComponent(el);
        }
        adminPane.setDisable(false);
    }
    private void readingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        MyAlerts.warningAlert("Thread Failed: " + e.getMessage());
        adminPane.setDisable(false);
    }

    private SaveCSV<Components> saveCSV;
    @FXML void save(){
        ArrayList<Components> componentsToSave = new ArrayList<>(DataCollection.components);
        String melding = "filen din blir lagert i denne plasering: src\\database\\lagringsPlass" +
                "\ngi filen din et navn: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 =pathStr + ".csv";
        String totalPathStr = "src\\database\\lagringsPlass"+"\\"+pathStr1;
        if(!pathStr.isEmpty()){
            saveCSV = new SaveCSV<>(componentsToSave,totalPathStr);
            saveCSV.setOnSucceeded(this::writingDone);
            saveCSV.setOnFailed(this::writingFailed);
            Thread th = new Thread(saveCSV);
            th.setDaemon(true);
            adminPane.setDisable(true);
            th.start();
        }else{
            MyAlerts.warningAlert("Ingen fil er valgt");
        }
    }
    private void writingDone(WorkerStateEvent e){
        saveCSV.call();
        adminPane.setDisable(false);
    }
    private void writingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        MyAlerts.warningAlert("Thread Failed: " + e.getMessage());
        adminPane.setDisable(false);
    }

    @FXML void loggUt(){
        Stage stage = (Stage) adminPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
        DataCollection.saveBinData(BINPATH);
    }
}
