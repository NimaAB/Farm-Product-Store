package org.app.controllers;

import io.FileClient;
import org.app.Load;
import org.app.PathDialogBox;
import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import dataModels.dataCollection.ListViewCollection;
import dataModels.dataCollection.TableViewCollection;
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
    @FXML
    BorderPane customerPane;
    @FXML
    TableView<Components> costumerTV;
    @FXML
    TextField txtFilter;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ListView<ConfigurationItems> shoppingCart;
    @FXML
    private Label totalPriceLbl;

    private PathDialogBox pathDialogBox = new PathDialogBox();
    private String openedFile;

    private void setOpenedFile(String openedFile) {
        this.openedFile = openedFile;
    }

    private String getOpenedFile() {
        return openedFile;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // inneholder komponenter som vises i tableView
        String file = "DataFraApp\\Database\\components.bin";
        TableViewCollection.loadComponents(file);
        TableViewCollection.setTableView(costumerTV);
        TableViewCollection.filterTableView(costumerTV, txtFilter);
        ListViewCollection.fillCategoryComboBox(categoryComboBox);
        ListViewCollection.setListView(shoppingCart);
        shoppingCart.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    void open() {
        String path = pathDialogBox.getPathToOpen();
        FileClient<ConfigurationItems> file = new FileClient<>(path);
        file.open();
        setOpenedFile(path);
        ListViewCollection.setOpenedFile(path);
        ListViewCollection.setOpen(true);
    }

    private String getPath() {
        if (getOpenedFile() == null) {
            return pathDialogBox.getPathToSave();
        }

        boolean newFile = Alerts.confirm("Do you want to save the configuration as a new configuration?");
        if (newFile) {
            return pathDialogBox.getPathToSave();
        } else {
            return getOpenedFile();
        }
    }

    @FXML
    void save(ActionEvent event) {
        ArrayList<ConfigurationItems> configToSave = new ArrayList<>(ListViewCollection.getConfigItems());
        if (!configToSave.isEmpty()) {
            String path = getPath();
            FileClient<ConfigurationItems> file = new FileClient<>(configToSave, path);
            file.save();
            return;
        }
        Alerts.warning("Nothing do save!");
    }

    @FXML
    void changeTable() {
        String choosenCatogry = categoryComboBox.getValue();
        ListViewCollection.selectedTable(choosenCatogry, costumerTV);
    }

    @FXML
    void addItemToCart() {
        ListViewCollection.addToShoppingCart();
        ListViewCollection.showTotalPrice(totalPriceLbl);
    }

    @FXML
    void clearList() {
        ListViewCollection.clearList();
        ListViewCollection.showTotalPrice(totalPriceLbl);
    }

    @FXML
    void deleteItem() {
        ObservableList<ConfigurationItems> selectedItems = shoppingCart.getSelectionModel().getSelectedItems();
        ListViewCollection.deleteItemList(selectedItems, totalPriceLbl);

    }

    @FXML
    void logOut(ActionEvent event) {
        if (ListViewCollection.isModified() && ListViewCollection.isOpen()) {
            boolean response = Alerts.confirm("Vil du lagre endringer på konfigurasjonen før du logger ut?");
            if (response) {
                save(event);
                Alerts.success("Konfigurasjonen er lagret");
            }
            ListViewCollection.clearList();
            Stage stage = (Stage) customerPane.getScene().getWindow();
            Load.window("org/app/loginView.fxml", "Login", stage);
        } else if (ListViewCollection.isModified()) {
            boolean response = Alerts.confirm("Vil du lagre konfigurasjonen før du logger ut?");
            if (response) {
                save(event);
                Alerts.success("Konfigurasjonen er lagret");
            }
            ListViewCollection.clearList();
            Stage stage = (Stage) customerPane.getScene().getWindow();
            Load.window("loginView.fxml", "Login", stage);
        } else {
            Stage stage = (Stage) customerPane.getScene().getWindow();
            Load.window("loginView.fxml", "Login", stage);
        }
    }
}
