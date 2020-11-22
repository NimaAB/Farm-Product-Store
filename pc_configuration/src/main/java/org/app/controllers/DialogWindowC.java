package org.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.app.Load;
import org.app.WindowLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogWindowC  {
    private String info;
    @FXML
    private Label infoLbl;
    @FXML
    private TextField pathTxt;

   /* @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }*/

    @FXML
    private void done(){
        //closes the window.
        getPath();
        WindowLoader wl = new WindowLoader();
        wl.exit();
    }
    private void setPathTxt(){

    }
    public String getPath(){
        return (!pathTxt.getText().isBlank())?pathTxt.getText() : "";
    }

    public void setInfo(String info) {
        infoLbl.setText(info);
    }


}
