package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/loginView.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setMaxWidth(1200);
        primaryStage.setMaxHeight(700);
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(800);
        primaryStage.setHeight(700);
        primaryStage.setWidth(1200);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        String FILE_DATABASE = "src/database/components.bin";
        primaryStage.setOnCloseRequest(e -> Load.exit(primaryStage,FILE_DATABASE));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
