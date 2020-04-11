package app.controllers;

import app.Load;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {


    @FXML BorderPane adminPane;
    @FXML TextField txtKomponentNr, txtKomponentNavn, txtPris, txtFilter;
    @FXML TextArea txtSpesifikasjoner;
    @FXML ComboBox<String> optKategorier;
    @FXML ComboBox<String> optFilterBy; // dere bestemmer om vi skal ha den her eller ikke - det som står ved siden av filter textfield

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML void opprettKomponent(ActionEvent event){
    }

    @FXML void slett(ActionEvent event){

    }

    @FXML void loggUt(ActionEvent event){
        Stage stage = (Stage) adminPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
    }
}
