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
import validations.Alerts;
import validations.customExceptions.InvalidFileException;
import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private BorderPane adminPane;
    @FXML private TextField nr, name, category, price, txtFilter;
    @FXML private TextArea specifications;
    @FXML private ComboBox<String> categoriesCombobox;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private TableView<Components> tableview;
    private String file = "src/database/components.bin";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataCollection.loadComponents(file);
        DataCollection.setTableView(tableview);
        DataCollection.fillFilterComboBox(filterComboBox);
        DataCollection.filterTableView(tableview,txtFilter);
        Categories.fillCategoryCombobox(categoriesCombobox,category);
    }

    @FXML void createComponents(){
        try {
            String nr = this.nr.getText();
            String name = this.name.getText();
            String category = this.category.getText();
            String specs = specifications.getText();
            String price = this.price.getText();
            CheckBox b = new CheckBox();

            Components component = new Components(nr,name,category,specs,price,b);
            DataCollection.addComponent(component);
            resetFields();

            Alerts.success("Komponent Opprettet");
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
        }
    }

    private void resetFields(){
        nr.setText("");
        name.setText("");
        category.setText("");
        specifications.setText("");
        price.setText("");
    }

    @FXML void delete(){
        DataCollection.deleteSelectedComponents();
        tableview.refresh();
    }

    private OpenCSV<Components> openCSV;
    @FXML void open(){
        String melding = "last opp en fil til programmet fra denne plassering: src\\database\\lagringsPlass " +
                         "\nSkriv navnet til filen du vil laste opp: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 = "src\\database\\lagringsPlass\\";
        String totalPathStr = pathStr1 + pathStr + ".csv";
        if(pathStr != null){
            //Gjør det mulig å bruke alle metoder fra dataCollection til filen bruker åpner
            DataCollection.loadComponents(totalPathStr);
            file = totalPathStr;
            openCSV = new OpenCSV<>(totalPathStr);
            openCSV.setOnSucceeded(this::readingDone);
            openCSV.setOnFailed(this::readingFailed);
            Thread th = new Thread(openCSV);
            adminPane.setDisable(true);
            th.setDaemon(true);
            th.start();
        } else {
            Alerts.warning("Ingen fil er valgt");
        }
    }

    private void readingDone(WorkerStateEvent e){
        try {
            ArrayList<Components> componentsList = openCSV.call();
            for(Components el:componentsList){
                DataCollection.addComponent(el);
            }
        } catch (InvalidFileException exception){
            Alerts.warning(exception.getMessage());
        }
        adminPane.setDisable(false);
    }

    private void readingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        Alerts.warning("Thread Failed: " + e.getMessage());
        adminPane.setDisable(false);
    }

    private SaveCSV<Components> saveCSV;
    @FXML void save(){
        ArrayList<Components> componentsToSave = new ArrayList<>(DataCollection.components);
        String melding = "filen din blir lagert i denne plasering: src\\database\\lagringsPlass" +
                         "\nGi filen din et navn: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 = pathStr + ".csv";
        String totalPathStr = "src\\database\\lagringsPlass\\" + pathStr1;
        if(!pathStr.isEmpty()){
            saveCSV = new SaveCSV<>(componentsToSave,totalPathStr);
            saveCSV.setOnSucceeded(this::writingDone);
            saveCSV.setOnFailed(this::writingFailed);
            Thread th = new Thread(saveCSV);
            adminPane.setDisable(true);
            th.setDaemon(true);
            th.start();
        } else {
            Alerts.warning("Ingen fil er valgt");
        }
    }

    private void writingDone(WorkerStateEvent e){
        saveCSV.call();
        adminPane.setDisable(false);
    }

    private void writingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        Alerts.warning("Thread Failed: " + e.getMessage());
        adminPane.setDisable(false);
    }

    @FXML void logOut(){
        Stage stage = (Stage) adminPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
        DataCollection.saveData();
    }
}
