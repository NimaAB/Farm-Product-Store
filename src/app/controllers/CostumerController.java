package app.controllers;

import app.Load;
import dataModels.data.Components;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CostumerController {
    @FXML
    BorderPane customerPane;
    //ArrayList av configuration:


    @FXML
    void open(ActionEvent event){
        //brukes for csv fil.
    }

    @FXML
    void save(ActionEvent event){
         //brukes for csv fil.
    }

    @FXML
    void loggUt(ActionEvent event){
        Stage stage = (Stage) customerPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
    }

}
