package app;

import dataModels.data.DataCollection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import validations.MyAlerts;
import java.io.IOException;

public class Load {

    /** LOADS AN FXML FILE */
    public static void window(String FXMLFilepath, String title, Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(Load.class.getResource(FXMLFilepath));
            Parent root = loader.<Parent>load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException e){ e.printStackTrace(); }
    }

    /** THIS METHOD ASKS THE ADMIN IF THEY WANT TO SAVE THE CHANGES THEY MADE BEFORE QUITTING THE APP */
    public static void exit(Stage stage){
        if(DataCollection.isModified()){
            boolean response = MyAlerts.confirmAlert("Vil du lagre alle endringer?");

            if(response){
                DataCollection.saveData();
                MyAlerts.successAlert("Alle engringer er lagret");
            } else {
                MyAlerts.successAlert("Endringer er ikke lagret"); }

            stage.close();
        }
    }
}
