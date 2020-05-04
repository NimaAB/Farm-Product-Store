package app.controllers;

import app.Load;
import app.Open;
import app.Save;
import dataModels.data.Components;
import dataModels.dataCollection.TableViewCollection;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import validations.Alerts;
import validations.NumberConversion;
import validations.ioExceptions.InvalidFileNameException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private BorderPane adminPane;
    @FXML private TextField nr, name, price, txtFilter;
    @FXML private TextArea specifications;
    @FXML private ComboBox<String> categoriesCombobox;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private TableView<Components> tableview;
    @FXML private TableColumn<Components,String> categoryCol;
    @FXML private TableColumn<Components,Double> prisCol;
    @FXML private TableColumn<Components,Integer> nrCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String file = "DataFraApp/Database/components.bin";
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

            Components component = new Components(nr,name,category,specs,price,b);
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

    @FXML void open(){
        boolean doOpen= Alerts.confirm("Vil du erstatte dataen du har" +
                        " i tabellen med dataen som ligger i filen som du skal laste opp?");
        if(doOpen){
            try {
            String path = Save.pathDialog("DataFraApp");
            OpenCSV<Components> openCSV = new OpenCSV<>(path);
            Open<Components> open = new Open<>(adminPane,openCSV,null);
            open.openFile();
        } catch (InvalidFileNameException e){
            Alerts.warning(e.getMessage());
        }
        }else{
            Alerts.success("Filen ble ikke lasta opp, for å beholde dataene i tabellen.");
        }
    }

    @FXML void save(){
        ArrayList<Components> components = new ArrayList<>(TableViewCollection.getComponents());
        try {
            String path = Save.pathDialog("DataFraApp");
            SaveCSV<Components> saveCSV = new SaveCSV<>(components, path);
            Save<Components> saveObj = new Save<>(adminPane, saveCSV);
            saveObj.saveFile();
        } catch (Exception e){
            Alerts.warning("Lagring gikk feil, Grunn: " + e.getCause());
        }

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
        if(TableViewCollection.isModified()){
            boolean response = Alerts.confirm("Vil du lagre alle endringer?");
            if(response){
                TableViewCollection.saveData();
                Alerts.success("Alle endringer er lagret");
            } else {
                Alerts.success("Endringer er ikke lagret");
            }
            Stage stage = (Stage) adminPane.getScene().getWindow();
            Load.window("views/loginView.fxml","Login",stage);
        }
    }
}
