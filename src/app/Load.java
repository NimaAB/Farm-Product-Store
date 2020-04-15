package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Load {
    public static void window(String FXMLFilepath, String title, Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(Load.class.getResource(FXMLFilepath));
            Parent root = loader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException e){ e.printStackTrace(); }
    }
}
