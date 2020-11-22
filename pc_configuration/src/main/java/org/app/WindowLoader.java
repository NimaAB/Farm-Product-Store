package org.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowLoader {
    private Scene scene;
    private String fxml;
    public WindowLoader(String fxml){
        this.fxml = fxml;
    }
    public WindowLoader(){

    }

    public void loadNewWindow() throws IOException {
        scene = new Scene(loadFXML());
        setRoot();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(fxml);
        stage.show();
    }

    private Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void setRoot() throws IOException {
        scene.setRoot(loadFXML());
    }
    public void exit(){
        Stage stage = new Stage();
        stage.close();
    }

}
