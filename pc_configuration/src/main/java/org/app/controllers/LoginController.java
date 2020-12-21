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
    @FXML RadioButton isAdmin;
    private final User admin = new User("Admin","1234");
    private final User customer = new User("Customer","1234");
    private static String curUser;

    public static String getCurUser() {
        return curUser;
    }
    private void setCurUser(String user){
        curUser = user;
    }

    @FXML
    void loggInn(ActionEvent event) {
        Stage stage = (Stage) loginPane.getScene().getWindow();

        if(isAdmin()){
            Load.window("adminView.fxml","Admin",stage);
            setCurUser("Admin");
        } else if(isCustom()) {
            Load.window("customerView.fxml","Customer",stage);
            setCurUser("Customer");
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
