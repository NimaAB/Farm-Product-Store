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
            Parent root = loader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException e){ e.printStackTrace(); }
    }

    /** THIS METHOD ASKS THE ADMIN IF THEY WANT TO SAVE THE CHANGES THEY MADE BEFORE QUITTING THE APP */
    public static void exit(Stage stage, String FILE_DATABASE){
        if(DataCollection.isModified()){
            boolean response = MyAlerts.confirmAlert("Do want to save the changes you made?");

            if(response){
                DataCollection.saveBinData(FILE_DATABASE);
                MyAlerts.successAlert("All changes has been save.");
            } else {
                MyAlerts.successAlert("Changes discarded."); }

            stage.close();
        } else { System.out.println("BIN FILE IS UP TO DATE"); }
    }
}
