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
    @FXML TextField txtKomponentNr, txtKomponentNavn, txtPris;
    @FXML TextArea txtSpesifikasjoner;
    @FXML ComboBox<String> kategorier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Oppretter komponenter(items) med validering
    @FXML void opprettKomponent(ActionEvent event){

    }

    // Når en rad trykkes direkte på tabellen så kan vi slette den ved å trykke på slett knapp
    @FXML void slett(ActionEvent event){

    }

    // Endrer admin vindu til login vindu når logout knappen trykkes
    @FXML void loggUt(ActionEvent event){
        Stage stage = (Stage) adminPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
    }

    /* Hva vi mangler:
    * 1. Metode for auto lagring
    * 2. Metode/Kode for tråder
    * 3. Metode/Kode for fil lagring
    *
    * Åpen for diskusjon / spørsmål:
    * 1. Trenger vi to tableviewer i admin vindu?
    *       - komponenter tabell - den må vi ha
    *       - konfigurert produkt tabell
    *           - det står på oppgaven at kunde skal kunne se tidligere konfigurerte produkter
    *           - skal vi gi den mulighet til administratoren også?
    *
    * 2. Trenger vi å lagre komponenter som CSV fil også? */
}
