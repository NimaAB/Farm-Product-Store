package app.controllers;

import app.Load;
import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import dataModels.dataCollection.ListViewCollection;
import dataModels.dataCollection.TableViewCollection;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
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

public class CustomerController implements Initializable {
    @FXML BorderPane customerPane;
    @FXML TableView<Components> costumerTV;
    @FXML TextField txtFilter;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ListView <ConfigurationItems> shoppingCart;
    @FXML private Label totalPriceLbl;
    private final String file = "src/database/components.bin"; // inneholder komponenter som vises i tableView

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableViewCollection.loadComponents(file);
        TableViewCollection.setTableView(costumerTV);
        TableViewCollection.filterTableView(costumerTV,txtFilter);
        ListViewCollection.fillCategoryComboBox(categoryComboBox);
        ListViewCollection.setListView(shoppingCart);
    }

    private OpenCSV<ConfigurationItems> openCSV;
    @FXML
    void open(ActionEvent event){
        String melding = "last opp en fil til programmet fra denne plassering: src\\database\\lagringsPlass " +
                "\nSkriv navnet til filen du vil laste opp: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 = "src\\database\\lagringsPlass\\";
        String totalPathStr = pathStr1 + pathStr + ".csv";
        if(pathStr != null){
            ListViewCollection.setOpenedFile(totalPathStr);
            ListViewCollection.setOpen(true);
            openCSV = new OpenCSV<>(totalPathStr);
            openCSV.setOnSucceeded(this::readingDone);
            openCSV.setOnFailed(this::readingFailed);
            Thread thread = new Thread(openCSV);
            customerPane.setDisable(true);
            thread.setDaemon(true);
            thread.start();
        }
    }
    private void readingDone(WorkerStateEvent e) {
        try {
            ArrayList<ConfigurationItems> configFromFile = openCSV.call();
            ListViewCollection.loadingConfig(configFromFile);
            ListViewCollection.showTotalPrice(totalPriceLbl);
            ListViewCollection.setModified(false);
        } catch (InvalidFileException exception){
            Alerts.warning(exception.getMessage());
            exception.printStackTrace();
        }
        customerPane.setDisable(false);
    }
    private void readingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        Alerts.warning("Thread Failed: " + e.getMessage());
        e.printStackTrace();
        customerPane.setDisable(false);
    }
    private SaveCSV<ConfigurationItems>saveCSV;
    @FXML
    void save(ActionEvent event){
        ArrayList<ConfigurationItems> configToSave = new ArrayList<>(ListViewCollection.getConfigItems());
        String melding = "filen din blir lagert i denne plasering: src\\database\\lagringsPlass" +
                "\nGi filen din et navn: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 = pathStr + ".csv";
        String totalPathStr = "src\\database\\lagringsPlass\\" + pathStr1;
        if(!pathStr.isEmpty()){
            ListViewCollection.setModified(false);
            saveCSV = new SaveCSV<>(configToSave,totalPathStr);
            saveCSV.setOnSucceeded(this::writingDone);
            saveCSV.setOnFailed(this::writingFailed);
            Thread thread = new Thread(saveCSV);
            customerPane.setDisable(true);
            thread.setDaemon(true);
            thread.start();
        }
    }
    private void writingDone(WorkerStateEvent e){
        saveCSV.call();
        customerPane.setDisable(false);
    }
    private void writingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        Alerts.warning("Thread Failed: "+e.getMessage());
        customerPane.setDisable(false);
    }

    @FXML
    void changeTable() {
        String choosenCatogry = categoryComboBox.getValue();
        ListViewCollection.selectedTable(choosenCatogry,costumerTV);
    }

    @FXML
    void addItemToCart() {
        ListViewCollection.addToShoppingCart();
        ListViewCollection.showTotalPrice(totalPriceLbl);
    }

    @FXML void clearList() {
        ListViewCollection.clearList();
        ListViewCollection.showTotalPrice(totalPriceLbl);
    }

    @FXML
    void deleteItem() {
        ObservableList<ConfigurationItems> selectedItems = shoppingCart.getSelectionModel().getSelectedItems();
        ListViewCollection.deleteItemList(selectedItems,totalPriceLbl);
    }

    @FXML
    void loggUt(ActionEvent event){
        // hvis man åpner en fil og legger nye items i den, spør programmen om å lagre endringer ved logg ut
        if(ListViewCollection.isModified() && ListViewCollection.isOpen()){
            boolean response = Alerts.confirm("Vil du lagre endringer på konfigurasjonen før du logger ut?");
            if(response){
                ArrayList<ConfigurationItems> toSave = new ArrayList<>(ListViewCollection.getConfigItems());
                String openedFilepath = ListViewCollection.getOpenedFile();
                SaveCSV<ConfigurationItems> saveCSV = new SaveCSV<>(toSave,openedFilepath);
                saveCSV.call();
                Alerts.success("Konfigurasjonen er lagret");
            }
            ListViewCollection.clearList();
            Stage stage = (Stage) customerPane.getScene().getWindow();
            Load.window("views/loginView.fxml","Login",stage);

        // hvis man logger ut uten å lagre konfigurasjonen man har gjort, spør programmen deg om du vil lagre den
        } else {
            boolean response = Alerts.confirm("Vil du lagre konfigurasjonen før du logger ut?");
            if(response){
                save(event);
                Alerts.success("Konfigurasjonen er lagret");
            }
            ListViewCollection.clearList();
            Stage stage = (Stage) customerPane.getScene().getWindow();
            Load.window("views/loginView.fxml", "Login", stage);
        }
    }
}
