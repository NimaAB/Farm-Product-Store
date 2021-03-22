package org.app.controllers;


import dataModels.data.ConfigItem;
import io.FileInfo;
import io.IOClient;
import org.app.Load;
import org.app.PathDialogBox;
import dataModels.data.Component;
import dataModels.dataCollection.TableViewCollection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import validations.Alerts;
import validations.NumberConversion;
import validations.ioExceptions.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private BorderPane adminPane;
    @FXML private TextField nr, name, price, txtFilter;
    @FXML private TextArea specifications;
    @FXML private ComboBox<String> categoriesCombobox;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private TableView<Component> tableview;
    @FXML private TableColumn<Component,String> categoryCol;
    @FXML private TableColumn<Component,Double> prisCol;
    @FXML private TableColumn<Component,Integer> nrCol;
    private String openedFile;
    private PathDialogBox pathDialogBox = new PathDialogBox();
    private void setOpenedFile(String openedFile) {
        this.openedFile = openedFile;
    }
    private String getOpenedFile(){
        return openedFile;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String file = "pc_configuration/DataFraApp/Database/components.bin";
        TableViewCollection.loadComponents(file);
        TableViewCollection.setTableView(tableview);
        TableViewCollection.fillFilterComboBox(filterComboBox);
        TableViewCollection.filterTableView(tableview,txtFilter);
        TableViewCollection.fillCategoryComboBox(categoriesCombobox);

        categoryCol.setCellFactory(ComboBoxTableCell.forTableColumn(TableViewCollection.getCategories()));
        nrCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberConversion.StringtoInteger()));
        prisCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberConversion.StringToDouble()));
    }

    @FXML void createComponents(){
        try {
            String nr = this.nr.getText();
            String name = this.name.getText();
            String category = categoriesCombobox.getValue();
            String specs = specifications.getText();
            String price = this.price.getText();
            CheckBox b = new CheckBox();

            Component component = new Component(nr,name,category,specs,price,b);
            TableViewCollection.addComponent(component);
            reset();

            Alerts.success("Komponent Opprettet");
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
        }
    }

    private void reset(){
        // Resetter feltene
        nr.setText("");
        name.setText("");
        specifications.setText("");
        price.setText("");

        // legger nye kategorier på dropdown om det finnes
        TableViewCollection.fillCategoryComboBox(categoriesCombobox);
        categoryCol.setCellFactory(ComboBoxTableCell.forTableColumn(TableViewCollection.getCategories()));
    }

    @FXML
    void open(){
        boolean doOpen= Alerts.confirm("Vil du erstatte dataen du har" +
                        " i tabellen med dataen som ligger i filen som du skal laste opp?");
        if(doOpen){
            String path = pathDialogBox.getPathToOpen();
            try{
                pathDialogBox.nullPathHandling(path);
                pathDialogBox.extensionCheck(path);
                pathDialogBox.fileNotFound(path);
            }catch (FileDontExistsException| NullPointerException | InvalidExtensionException e){
                Alerts.warning(e.getMessage());
                return;
            }
            FileInfo file = new FileInfo(path);
            IOClient<Component> io = new IOClient<>(file);
            io.runOpenThread();
            setOpenedFile(path);
            TableViewCollection.setLoadedFile(path);
        }else{
            Alerts.success("Your data isn't changed.");
        }
    }

    private String getPath(){
        if(getOpenedFile()==null){
            return pathDialogBox.getPathToSave();
        }

        boolean newFile = Alerts.confirm("Vil du lagre filen som en ny fil?");
        if(newFile){
            return pathDialogBox.getPathToSave();
        }else{
            return getOpenedFile();
        }
    }

    @FXML void save(){
        ArrayList<Component> components = new ArrayList<>(TableViewCollection.getComponents());
        if(!components.isEmpty()){
            String path = getPath();
            try{
                pathDialogBox.nullPathHandling(path);
                pathDialogBox.extensionCheck(path);
            }catch (NullPointerException | InvalidExtensionException e){
                Alerts.warning(e.getMessage());
                return;
            }
            FileInfo file = new FileInfo(path);
            IOClient<Component> io = new IOClient<>(file, components);
            io.runSaveThread();
        }
        else{
            Alerts.warning("There is nothing to save!");
        }
    }

    @FXML void nameEdited(TableColumn.CellEditEvent<Component, String> event){
        try {
            event.getRowValue().setComponentName(event.getNewValue());
            TableViewCollection.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML void specsEdited(TableColumn.CellEditEvent<Component, String> event){
        try {
            event.getRowValue().setComponentSpecs(event.getNewValue());
            TableViewCollection.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML void categoryEdited(TableColumn.CellEditEvent<Component, String> event){
        try {
            event.getRowValue().setComponentCategory(event.getNewValue());
            TableViewCollection.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML void nrEdited(TableColumn.CellEditEvent<Component, Integer> event){
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

    @FXML void priceEdited(TableColumn.CellEditEvent<Component, Double> event){
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
        if(TableViewCollection.isModified()){
            boolean response = Alerts.confirm("Vil du lagre alle endringer?");
            if(response){
                TableViewCollection.saveData();
                Alerts.success("Alle endringer er lagret");
            } else {
                Alerts.success("Endringer er ikke lagret");
                TableViewCollection.setReloadComponents(true);
                TableViewCollection.getComponents().clear();
                TableViewCollection.setModified(false);
            }
            Stage stage = (Stage) adminPane.getScene().getWindow();
            Load.window("loginView.fxml","Login",stage);
        } else {
            Stage stage = (Stage) adminPane.getScene().getWindow();
            Load.window("loginView.fxml","Login",stage);
        }
    }
}
