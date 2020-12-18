package org.app.controllers;

import dataModels.data.ConfigItem;
import io.FileClient;
import org.app.Load;
import org.app.PathDialogBox;
import dataModels.data.Component;
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
import validations.ioExceptions.FileDontExistsException;
import validations.ioExceptions.InvalidExtensionException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    BorderPane customerPane;
    @FXML
    TableView<Component> costumerTV;
    @FXML
    TextField txtFilter;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ListView<ConfigItem> shoppingCart;
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
        try{
            pathDialogBox.nullPathHandling(path);
            pathDialogBox.extensionCheck(path);
            pathDialogBox.fileNotFound(path);
        }catch (FileDontExistsException| NullPointerException | InvalidExtensionException e){
            Alerts.warning(e.getMessage());
            return;
        }
        FileClient<ConfigItem> file = new FileClient<>(path);
        ArrayList<ConfigItem> list = file.open();
        ListViewCollection.loadingConfig(list);
        ListViewCollection.showTotalPrice(totalPriceLbl);
        ListViewCollection.setModified(false);
        setOpenedFile(path);
        ListViewCollection.setOpenedFile(path);
        ListViewCollection.setOpen(true);
    }

    private String getPath() {
        if (getOpenedFile() == null) {
            return pathDialogBox.getPathToSave();
        }

        boolean newFile = Alerts.confirm("Do you want to save this configuration as a new configuration?");
        if (newFile) {
            return pathDialogBox.getPathToSave();
        } else {
            return getOpenedFile();
        }
    }

    @FXML
    void save(ActionEvent event) {
        ArrayList<Component> configToSave = new ArrayList<>(ListViewCollection.getConfigItems());
        if (!configToSave.isEmpty()) {
            String path = getPath();
            try{
                pathDialogBox.nullPathHandling(path);
                pathDialogBox.extensionCheck(path);
            }catch (NullPointerException | InvalidExtensionException e){
                Alerts.warning(e.getMessage());
                return;
            }
            FileClient<Component> file = new FileClient<>(configToSave, path);
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
        ObservableList<ConfigItem> selectedItems = shoppingCart.getSelectionModel().getSelectedItems();
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
