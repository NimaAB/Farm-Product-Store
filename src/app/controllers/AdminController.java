package app.controllers;

import app.Load;
import dataModels.data.Categories;
import dataModels.data.Components;
import dataModels.dataCollection.TableViewCollection;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import validations.Alerts;
import validations.NumberConversion;
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
    @FXML private TableColumn<Components,Double> prisCol;
    @FXML private TableColumn<Components,Integer> nrCol;
    private String file = "src/database/components.bin";
    private OpenCSV<Components> openCSV;
    private SaveCSV<Components> saveCSV;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableViewCollection.loadComponents(file);
        TableViewCollection.setTableView(tableview);
        TableViewCollection.fillFilterComboBox(filterComboBox);
        TableViewCollection.filterTableView(tableview,txtFilter);
        Categories.fillCategoryCombobox(categoriesCombobox,category);

        nrCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberConversion.StringtoInteger()));
        prisCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberConversion.StringToDouble()));
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
            TableViewCollection.addComponent(component);
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

    @FXML void open(){
        String melding = "last opp en fil til programmet fra denne plassering: src\\database\\lagringsPlass\nSkriv navnet til filen du vil laste opp: ";
        String filename = JOptionPane.showInputDialog(null,melding);
        String filepath = "src\\database\\lagringsPlass\\" + filename + ".csv";

        if(filename != null){
            TableViewCollection.loadComponents(filepath);
            file = filepath;
            openCSV = new OpenCSV<>(filepath);
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

    @FXML void save(){
        ArrayList<Components> componentsToSave = new ArrayList<>(TableViewCollection.getComponents());
        String melding = "filen din blir lagert i denne plasering: src\\database\\lagringsPlass\nGi filen et navn: ";
        String filename = JOptionPane.showInputDialog(null,melding);
        String filepath = "src\\database\\lagringsPlass\\" + filename + ".csv";

        if(!filename.isEmpty()){
            saveCSV = new SaveCSV<>(componentsToSave,filepath);
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

    private void readingDone(WorkerStateEvent e){
        try {
            ArrayList<Components> componentsList = openCSV.call();
            for(Components el:componentsList){
                TableViewCollection.addComponent(el);
            }
        } catch (InvalidFileException exception){
            Alerts.warning(exception.getMessage());
        } catch (ClassCastException ignored){
            Alerts.warning("Filen er ugyldig eller inneholder feil data. Prøv å åpne en annen fil");
        }
        adminPane.setDisable(false);
    }

    private void readingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        Alerts.warning("Thread Failed: " + e.getMessage());
        adminPane.setDisable(false);
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

    @FXML void nameEdited(TableColumn.CellEditEvent<Components, String> event){
        try {
            event.getRowValue().setComponentName(event.getNewValue());
            TableViewCollection.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML void specsEdited(TableColumn.CellEditEvent<Components, String> event){
        try {
            event.getRowValue().setComponentSpecs(event.getNewValue());
            TableViewCollection.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML void categoryEdited(TableColumn.CellEditEvent<Components, String> event){
        try {
            event.getRowValue().setComponentCategory(event.getNewValue());
            TableViewCollection.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML void nrEdited(TableColumn.CellEditEvent<Components, Integer> event){
        try {
            event.getRowValue().setComponentNr(Integer.toString(event.getNewValue()));
            TableViewCollection.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        } catch (NullPointerException ignored) {
            tableview.refresh();
        }
    }

    @FXML void priceEdited(TableColumn.CellEditEvent<Components, Double> event){
        try {
            event.getRowValue().setComponentPrice(Double.toString(event.getNewValue()));
            TableViewCollection.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        } catch (NullPointerException ignored) {
            tableview.refresh();
        }
    }

    @FXML void delete(){
        TableViewCollection.deleteSelectedComponents();
        tableview.refresh();
    }

    @FXML void logOut(){
        Stage stage = (Stage) adminPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
        TableViewCollection.saveData();
    }
}
