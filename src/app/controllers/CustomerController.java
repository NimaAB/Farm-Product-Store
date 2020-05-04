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
import validations.ioExceptions.InvalidExtensionException;
import validations.ioExceptions.InvalidFileNameException;

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

    private String openedFile;
    private void setOpenedFile(String openedFile) {
        this.openedFile = openedFile;
    }
    private String getOpenedFile(){
        return openedFile;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // inneholder komponenter som vises i tableView
        String file = "DataFraApp\\Database\\components.bin";
        TableViewCollection.loadComponents(file);
        TableViewCollection.setTableView(costumerTV);
        TableViewCollection.filterTableView(costumerTV,txtFilter);
        ListViewCollection.fillCategoryComboBox(categoryComboBox);
        ListViewCollection.setListView(shoppingCart);
        shoppingCart.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    void open() {
        try {
            String path = Save.pathDialog("DataFraApp");
            String extention = Save.extension(path);
            if(extention.equals(".csv")){
                OpenCSV<Components> openCSV = new OpenCSV<>(path);
                Open<Components> open = new Open<>(customerPane,openCSV,totalPriceLbl);
                open.openFile();
                setOpenedFile(path);
                ListViewCollection.setOpenedFile(path);
                ListViewCollection.setOpen(true);
            }else{
                Alerts.warning("En PC-Konfigurasjon kan bare være i en csv fil!");
            }
        } catch (InvalidFileNameException| InvalidExtensionException e){
            Alerts.warning(e.getMessage());
        }catch (NullPointerException ignored){}
    }

    @FXML
    void save(ActionEvent event){
        ArrayList<ConfigurationItems> configToSave = new ArrayList<>(ListViewCollection.getConfigItems());
        if(!configToSave.isEmpty()){
            String path;
            try{
                if(getOpenedFile()!=null){
                   boolean newFile = Alerts.confirm("Vil du lagre endringene som en ny PC-konfiguration?");
                    if(newFile){
                        path = Save.pathDialog("DataFraApp");
                    }else{
                        path = getOpenedFile();
                    }
                }else{
                    path = Save.pathDialog("DataFraApp");
                }
                String extention = Save.extension(path);
                if(extention.equals(".csv")){
                    SaveCSV<ConfigurationItems> saveCSV = new SaveCSV<>(configToSave, path);
                    Save<ConfigurationItems> saveObj = new Save<>(customerPane, saveCSV,null);
                    saveObj.saveFile();
                }else{
                    Alerts.warning("Programmet kan lagre PC-Konfigurasjon bare som csv fil!");
                }
            }catch (InvalidFileNameException e){
                Alerts.warning("Lagring gikk feil, Grunn: " + e.getCause());
            }catch (InvalidExtensionException e){
                Alerts.warning(e.getMessage());
            }catch (NullPointerException ignored){}
        }else{
            Alerts.warning("Det er ingen data for å lagre til fil.");
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
        if(ListViewCollection.isModified() && ListViewCollection.isOpen()){
            boolean response = Alerts.confirm("Vil du lagre endringer på konfigurasjonen før du logger ut?");
            if(response){
                save(event);
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
