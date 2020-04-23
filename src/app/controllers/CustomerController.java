package app.controllers;

import app.Load;
import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import dataModels.data.DataCollection;
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
import validations.MyAlerts;
import validations.customExceptions.InvalidFileException;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML BorderPane customerPane;
    @FXML TableView<Components> costumerTV;
    @FXML TextField txtFilter;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ListView <ConfigurationItems> shoppingCart;
    @FXML private Label totalPriceLbl;

    private String file = "src/database/components.bin";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataCollection.loadComponents(file);
        DataCollection.setTableView(costumerTV);
        DataCollection.filterTableView(costumerTV,txtFilter);
        DataCollection.fillCategoryComboBox(categoryComboBox);
        DataCollection.setListView(shoppingCart);
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
            file = totalPathStr;
            openCSV = new OpenCSV<>(file);
            openCSV.setOnSucceeded(this::readingDone);
            openCSV.setOnFailed(this::readingFailed);
            Thread thread = new Thread(openCSV);
            customerPane.setDisable(true);
            thread.setDaemon(true);
            thread.start();
        }
    }
    private void readingDone(WorkerStateEvent e) {
        try{
            ArrayList<ConfigurationItems> configFromFile = openCSV.call();
            DataCollection.loadingConfig(configFromFile);
            DataCollection.showTotalPrice(totalPriceLbl);
        }catch (InvalidFileException exception){
            MyAlerts.warningAlert(exception.getMessage());
        }
        customerPane.setDisable(false);
    }
    private void readingFailed(WorkerStateEvent event){
        Throwable e = event.getSource().getException();
        MyAlerts.warningAlert("Thread Faild: " + e.getMessage());
        customerPane.setDisable(false);
    }
    private SaveCSV<ConfigurationItems>saveCSV;
    @FXML
    void save(ActionEvent event){
        //brukes for csv fil.
        ArrayList<ConfigurationItems> configToSave = new ArrayList<>(DataCollection.configItems);
        String melding = "filen din blir lagert i denne plasering: src\\database\\lagringsPlass" +
                "\nGi filen din et navn: ";
        String pathStr = JOptionPane.showInputDialog(null,melding);
        String pathStr1 = pathStr + ".csv";
        String totalPathStr = "src\\database\\lagringsPlass\\" + pathStr1;
        if(!pathStr.isEmpty()){
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
        MyAlerts.warningAlert("Thread Failed: "+e.getMessage());
        customerPane.setDisable(false);
    }

    @FXML
    void changeTable() {
        String choosenCatogry = categoryComboBox.getValue();
        DataCollection.selectedTable(choosenCatogry,costumerTV);
    }

    @FXML
    void addItemToCart() {
        DataCollection.addToShoppingCart();
        DataCollection.showTotalPrice(totalPriceLbl);
    }

    @FXML void clearList() {
        DataCollection.clearList();
        DataCollection.showTotalPrice(totalPriceLbl);
    }

    @FXML
    void deleteItem() {
        ObservableList<ConfigurationItems> selectedItems = shoppingCart.getSelectionModel().getSelectedItems();
        DataCollection.deleteItemList(selectedItems,totalPriceLbl);
    }

    @FXML
    void loggUt(ActionEvent event){
        Stage stage = (Stage) customerPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
    }

}
