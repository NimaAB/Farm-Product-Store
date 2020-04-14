package app.controllers;

import app.Load;
import dataModels.data.Categories;
import dataModels.data.Components;
import dataModels.data.DataCollection;
import database.componentsDB.RequestDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    Alert warning = new Alert(Alert.AlertType.WARNING);
    Alert success = new Alert(Alert.AlertType.INFORMATION);
    @FXML private BorderPane adminPane;
    @FXML private TextField nr, name, category, price, txtFilter;
    @FXML private TextArea specifications;
    @FXML private ComboBox<String> optCategories;
    @FXML private ComboBox<String> optFilterBy;
    @FXML private TableView<Components> tableview;
    private final String FILE_DATABASE = "src/database/componentsDB/dbComponents.bin";

    /**
     <ENDRINGER:>
        PRØV Å KJØRE PROGRAMMEN :)

        <NB>: Når man sletter eller oppretter komponenter, tar det 3 sek før den sKjer på grunn av tråder NIMA har laget

         1. Admin Controller blir oppdatert
         2. <Items> har jeg endret til <Components>
         3. Validations
               - Jeg har endret det på min del fordi jeg
                 vil ikke kalle validering hver gang jeg oppretter en ny komponent. Kontrolleren blir lang
                 Jeg har gjort det slik at hver gang jeg instansierer Components objektet blir den automatisk validert
                 sjekk: <Components> klasse
               - customExceptions kommer jeg til å bruke senere - Exceptions NIMA har laget
         4. DataCollection
               - Jeg har laget nye metoder
               - filtrering virker nå
                 sjekk: <DataCollection> klasse
         5. Database
               - Kan lagre komponenter i en database
               - Kan slette komponenter fra databasen
               - Oppdater tableviewen automatisk
                 sjekk: <RequestDatabase> klasse
         6. Categories
               - Måten jeg har tenkt på uten <enum>
               - Gir mulighet til brukeren til å legge nye kategorier

      <SJEKK NOEN ANDRE KOMMENTAR PÅ:>
         * 1. ItemsFormat
         * 2. DataCollection -> loadComponents metode
     **/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Categories.categoryOnChange(optCategories,category);
        DataCollection.loadComponents(FILE_DATABASE);
        DataCollection.setTableView(tableview);
        DataCollection.filterOnChange(optFilterBy);
        DataCollection.filterTableView(tableview,txtFilter);
    }

    @FXML void opprettKomponent(ActionEvent event){
        try {
            String nr = this.nr.getText();
            String name = this.name.getText();
            String category = this.category.getText();
            String specs = specifications.getText();
            String price = this.price.getText();
            CheckBox b = new CheckBox();

            Components component = new Components(nr,name,category,specs,price,b);
            RequestDatabase.toSaveComponent(component,FILE_DATABASE);

            success.setHeaderText("Component created");
            success.showAndWait();
        } catch (IllegalArgumentException e) {
            warning.setHeaderText(e.getMessage());
            warning.showAndWait();
        }
    }

    @FXML void slett(ActionEvent event){
        RequestDatabase.toDeleteSelectedComponents(FILE_DATABASE);
        tableview.refresh();
    }

    @FXML void loggUt(ActionEvent event){
        Stage stage = (Stage) adminPane.getScene().getWindow();
        Load.window("views/loginView.fxml","Login",stage);
    }
}
