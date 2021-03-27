package org.app.controllers;

import org.app.Load;
import org.app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import validations.Alerts;

public class LoginController {

    @FXML BorderPane loginPane;
    @FXML TextField txtUsername, txtPassword;
    private final User admin = new User("Admin","1234");


    @FXML
    void loggInn(ActionEvent event) {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        if(isValidUser()){
            Load.window("adminView.fxml","Admin",stage);
        } else{
            Alerts.warning("Feil Bruker navn eller passord!");
        }
    }
    private boolean isValidUser(){
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        boolean usernameMatches= username.equals(admin.getUserName());
        boolean passwordMatches= password.equals(admin.getPassword());
        return passwordMatches && usernameMatches;
    }
}
