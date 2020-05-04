package app.controllers;

import app.Load;
import app.Open;
import app.Save;
import dataModels.data.Components;
import dataModels.dataCollection.TableViewCollection;
import filehandling.bin.SaveBin;
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
import validations.ioExceptions.InvalidExtensionException;
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
    private String openedFile;

    private void setOpenedFile(String openedFile) {
        this.openedFile = openedFile;
    }
    private String getOpenedFile(){
        return openedFile;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String file = "DataFraApp\\Database\\components.bin";
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
                String extention = Save.extension(path);
                if (extention.equals(".csv")) {
                    OpenCSV<Components> openCSV = new OpenCSV<>(path);
                    Open<Components> open = new Open<>(adminPane, openCSV, null);
                    open.openFile();
                    setOpenedFile(path);
                } else {
                    Alerts.warning("Programmet åpner bare csv fil");
                }
                TableViewCollection.setLoadedFile(path);
        } catch (InvalidFileNameException | InvalidExtensionException e){
            Alerts.warning(e.getMessage());
        } catch (NullPointerException ignored){}
        }else{
            Alerts.success("Filen ble ikke lasta opp, for å beholde dataene i tabellen.");
        }
    }

    @FXML void save(){
        ArrayList<Components> components = new ArrayList<>(TableViewCollection.getComponents());
        if(!components.isEmpty()){
            String path;
            try {
                if(getOpenedFile()!=null){
                    boolean newFile = Alerts.confirm("Vil du lagre filen som en ny fil?");
                        if(newFile){
                            path = Save.pathDialog("DataFraApp");
                        }else{
                            path = getOpenedFile();
                        }
                    }
                else{
                    path = Save.pathDialog("DataFraApp");
                }
                String extention = Save.extension(path);
                switch (extention) {
                    case ".csv":
                        SaveCSV<Components> saveCSV = new SaveCSV<>(components, path);
                        Save<Components> saveObj = new Save<>(adminPane, saveCSV, null);
                        saveObj.saveFile();
                        break;
                    case ".bin":
                        SaveBin<Components> saveBin = new SaveBin<>(components, path);
                        Save<Components> save = new Save<>(adminPane, null, saveBin);
                        save.saveFile();
                        break;
                    default:
                        Alerts.warning("Programmet lagrer til bin og csv fil type");
                        break;
                }
            }catch (InvalidFileNameException e){
                Alerts.warning("Lagring gikk feil, Grunn: " + e.getMessage());
            }catch (InvalidExtensionException e){
                Alerts.warning(e.getMessage());
            }catch (NullPointerException ignored){}

        }else{
            Alerts.warning("Det er ingen data for å lagre til fil.");
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
                TableViewCollection.setReloadComponents(true);
                TableViewCollection.getComponents().clear();
                TableViewCollection.setModified(false);
            }
            Stage stage = (Stage) adminPane.getScene().getWindow();
            Load.window("views/loginView.fxml","Login",stage);
        } else {
            Stage stage = (Stage) adminPane.getScene().getWindow();
            Load.window("views/loginView.fxml","Login",stage);
        }
    }
}
