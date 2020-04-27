package app.controllers;

import app.Load;
import app.Open;
import app.Save;
import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import dataModels.dataCollection.ListViewCollection;
import dataModels.dataCollection.TableViewCollection;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import validations.Alerts;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // inneholder komponenter som vises i tableView
        String file = "DataFraApp/Database/components.bin";
        TableViewCollection.loadComponents(file);
        TableViewCollection.setTableView(costumerTV);
        TableViewCollection.filterTableView(costumerTV,txtFilter);
        ListViewCollection.fillCategoryComboBox(categoryComboBox);
        ListViewCollection.setListView(shoppingCart);
    }

    @FXML
    void open(ActionEvent event) {
        try {
            String path = Save.pathDialog("DataFraApp");
            OpenCSV<Components> openCSV = new OpenCSV<>(path);
            Open<Components> open = new Open<>(customerPane,openCSV,totalPriceLbl);
            open.openFile();
            ListViewCollection.setOpenedFile(path);
            ListViewCollection.setOpen(true);
        } catch (Exception e){
            Alerts.warning("Filen lastes ikke opp grunn: "+e.getCause());
        }
    }

    @FXML
    void save(ActionEvent event){
        ArrayList<ConfigurationItems> configToSave = new ArrayList<>(ListViewCollection.getConfigItems());
        try{
            String path = Save.pathDialog("DataFraApp");
            SaveCSV<ConfigurationItems> saveCSV = new SaveCSV<>(configToSave, path);
            Save<ConfigurationItems> saveObj = new Save<>(customerPane, saveCSV);
            saveObj.saveFile();
        }catch (Exception e){
            Alerts.warning("Lagring gikk feil, Grunn: " + e.getCause());
        }
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
    void logOut(ActionEvent event){
        // hvis man åpner en fil og legger nye items i den, spør programmen om å lagre endringer ved logg ut
        if(ListViewCollection.isModified() && ListViewCollection.isOpen()){
            boolean response = Alerts.confirm("Vil du lagre endringer på konfigurasjonen før du logger ut?");
            if(response){
                ListViewCollection.saveConfig();
                Alerts.success("Konfigurasjonen er lagret");
            }
            ListViewCollection.clearList();
            Stage stage = (Stage) customerPane.getScene().getWindow();
            Load.window("views/loginView.fxml","Login",stage);

        } else if(ListViewCollection.isModified()) {
            boolean response = Alerts.confirm("Vil du lagre konfigurasjonen før du logger ut?");
            if(response){
                save(event);
                Alerts.success("Konfigurasjonen er lagret");
            }
            ListViewCollection.clearList();
            Stage stage = (Stage) customerPane.getScene().getWindow();
            Load.window("views/loginView.fxml", "Login", stage);
        } else {
            Stage stage = (Stage) customerPane.getScene().getWindow();
            Load.window("views/loginView.fxml", "Login", stage);
        }
    }
}
