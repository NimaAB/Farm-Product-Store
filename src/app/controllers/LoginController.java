package app.controllers;

import app.Load;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML BorderPane loginPane;
    @FXML TextField txtUsername, txtPassword;
    @FXML CheckBox isAdmin;

    @FXML
    void loggInn(ActionEvent event) {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        if(isAdmin.isSelected()){
            Load.window("views/adminView.fxml","Admin",stage);
        } else {
            Load.window("views/customerView.fxml","Customer",stage); }
    }
}
