package app.controllers;

import app.Load;
import dataModels.data.Categories;
import dataModels.data.Components;
import dataModels.data.DataCollection;
import database.componentsDB.RequestDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    Alert warning = new Alert(Alert.AlertType.WARNING);
    Alert success = new Alert(Alert.AlertType.INFORMATION);
    @FXML private BorderPane adminPane;
    @FXML private TextField nr, name, category, price, txtFilter;
    @FXML private TextArea specifications;
    @FXML private ComboBox<String> optCategories;
    @FXML private ComboBox<String> optFilterBy;
    @FXML private TableView<Components> tableview;
    private final String FILE_DATABASE = "src/database/componentsDB/dbComponents.bin";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Categories.categoryOnChange(optCategories,category);
        DataCollection.loadComponents(FILE_DATABASE);
        DataCollection.setTableView(tableview);
        DataCollection.filterOnChange(optFilterBy);
        DataCollection.filterTableView(tableview,txtFilter);
    }

    @FXML void opprettKomponent(ActionEvent event){
        try {
            String nr = this.nr.getText();
            String name = this.name.getText();
            String category = this.category.getText();
            String specs = specifications.getText();
            String price = this.price.getText();
            CheckBox b = new CheckBox();

            Components component = new Components(nr,name,category,specs,price, b);
            RequestDatabase.toSaveComponent(component);

            success.setHeaderText("Component created");
            success.showAndWait();
        } catch (IllegalArgumentException e) {
            warning.setHeaderText(e.getMessage());
            warning.showAndWait();
        }
    }

    @FXML void slett(ActionEvent event){
        RequestDatabase.toDeleteSelectedComponents();
        tableview.refresh();
    }

    @FXML void loggUt(ActionEvent event){
        Stage stage = (Stage) adminPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
        RequestDatabase.toUpdateDatabase(FILE_DATABASE);
    }
}
