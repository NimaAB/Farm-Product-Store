package app.controllers;

import app.Load;

import app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import validations.Alerts;

public class LoginController {

    @FXML BorderPane loginPane;
    @FXML TextField txtUsername, txtPassword;
    @FXML RadioButton isAdmin;
    private final User admin = new User("Admin","1234");
    private final User customer = new User("Customer","1234");

    @FXML
    void loggInn(ActionEvent event) {
        Stage stage = (Stage) loginPane.getScene().getWindow();

        if(isAdmin()){
            Load.window("views/adminView.fxml","Admin",stage);
        } else if(isCustom()) {
            Load.window("views/customerView.fxml","Customer",stage);
        }else{
            Alerts.warning("Feil Bruker navn eller passord!");
        }
    }
    private boolean isAdmin(){
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        boolean usernameMatches= username.equals(admin.getUserName());
        boolean passwordMatches= password.equals(admin.getPassword());
        admin.setAdmin(true);
        return isAdmin.isSelected() && passwordMatches && usernameMatches;
    }
    private boolean isCustom(){
        String username= txtUsername.getText();
        String password= txtPassword.getText();
        boolean usernameMatches= username.equals(customer.getUserName());
        boolean passwordMatches= password.equals(customer.getPassword());
        admin.setAdmin(false);
        return usernameMatches && passwordMatches && !isAdmin.isSelected();
    }
}
