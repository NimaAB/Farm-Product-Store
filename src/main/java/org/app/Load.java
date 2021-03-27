package org.app;

import dataModels.dataCollection.TableViewCollection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import validations.Alerts;
import java.io.IOException;

public class Load {

    /** Laster opp en fxml fil */
    public static void window(String FXMLFilepath, String title, Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(Load.class.getResource(FXMLFilepath));
            Parent root = loader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.getScene().getWindow().centerOnScreen();
            stage.show();
        } catch (IOException e){ e.printStackTrace(); }
    }

    /** Spør brukeren om å lagre endringer før programmen slutter */
    public static void exit(Stage stage){
        if(TableViewCollection.isModified()){
            boolean response = Alerts.confirm("Vil du lagre alle endringer?");
            if(response){
                TableViewCollection.saveData();
                Alerts.success("Alle endringer er lagret");
            } else {
                Alerts.success("Endringer er ikke lagret"); }
        }
        stage.close();
    }
}
