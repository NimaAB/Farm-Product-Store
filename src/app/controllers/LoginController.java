package app.controllers;

import app.Load;
import filehandling.bin.OpenBin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class LoginController {
    protected static final String filePath= "data.bin";

    @FXML BorderPane loginPane;
    @FXML TextField txtUsername, txtPassword;
    @FXML CheckBox isAdmin;

    @FXML
    void loggInn(ActionEvent event) {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        if(isAdmin.isSelected()){
            Load.window("views/adminView.fxml","Admin",stage);
        } else {
            Load.window("views/costumerView.fxml","Customer",stage); }
    }
}
