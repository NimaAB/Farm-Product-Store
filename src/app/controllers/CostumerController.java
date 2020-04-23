package app.controllers;

import app.Load;
import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import dataModels.data.DataCollection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CostumerController implements Initializable {
    @FXML BorderPane customerPane;
    @FXML TableView<Components> costumerTV;
    @FXML TextField txtFilter;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ListView <ConfigurationItems> shoppingCart;
    @FXML private Label totalPriceLbl;

    private final String file = "src/database/components.bin";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataCollection.loadComponents(file);
        DataCollection.setTableView(costumerTV);
        DataCollection.filterTableView(costumerTV,txtFilter);
        DataCollection.fillCategoryComboBox(categoryComboBox);
        DataCollection.setListView(shoppingCart);
    }


    @FXML
    void open(ActionEvent event){
        //brukes for csv fil.
    }


    @FXML
    void save(ActionEvent event){
        //brukes for csv fil.
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
        DataCollection.deleteItemList(shoppingCart, totalPriceLbl);
    }

    @FXML
    void loggUt(ActionEvent event){
        Stage stage = (Stage) customerPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
    }

}
