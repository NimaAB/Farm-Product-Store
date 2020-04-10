package app.controllers;

import app.Load;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CostumerController {
    @FXML
    BorderPane customerPane;

    @FXML
    void loggUt(ActionEvent event){
        Stage stage = (Stage) customerPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
    }
}
