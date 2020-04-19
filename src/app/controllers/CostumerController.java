package app.controllers;

import app.Load;
import dataModels.data.Components;
import dataModels.data.DataCollection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class CostumerController implements Initializable {
    @FXML BorderPane customerPane;
    @FXML TableView<Components> costumerTV;
    @FXML TextField txtFilter;
    @FXML private ComboBox<String> filtherCatogry;
    @FXML private ListView <Components> shoppingCart;

    private final String file = "src/database/components.bin";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataCollection.loadComponents(file);
        DataCollection.setTableView(costumerTV);
        DataCollection.filterTableView(costumerTV,txtFilter);
        DataCollection.fillCombox(filtherCatogry);


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
    void changeTable(ActionEvent event) {
        String choosenCatogry = filtherCatogry.getValue().toString();
        DataCollection.selectedTable(choosenCatogry,costumerTV);
    }

    @FXML
    void addItemToCart(ActionEvent event) {
        DataCollection.addToShoppingCart(shoppingCart,costumerTV);
    }

    @FXML void clearList(ActionEvent event) {
        shoppingCart.setItems(null);
    }

    @FXML
    void loggUt(ActionEvent event){
        Stage stage = (Stage) customerPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
    }




}
