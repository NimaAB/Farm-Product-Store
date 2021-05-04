package org.app;

import org.app.data.dataCollection.CategoryCollection;
import org.app.data.dataCollection.TableViewCollection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.app.validation.Alerts;
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

    /** Åpner en ny vindu for kategori oppretting */
    public static void openCategoryPopup(){
        try {
            Load.window("category.fxml","Endre Kategori",new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Spør brukeren om å lagre endringer før programmen slutter */
    public static void exit(Stage stage){
        TableViewCollection collection = TableViewCollection.getINSTANCE();
        if(collection.isModified()){
            boolean response = Alerts.confirm("Vil du lagre alle endringer?");
            if(response){
                collection.saveData();
            } else {
                Alerts.success("Endringer er ikke lagret");
            }
        }
        CategoryCollection categoryCollection = CategoryCollection.getInstance();
        if(categoryCollection.isModified()) categoryCollection.saveCategories();
        stage.close();
    }
}
