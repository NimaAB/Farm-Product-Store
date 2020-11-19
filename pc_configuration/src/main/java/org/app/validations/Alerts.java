package org.app.validations;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerts {
    private static final Alert warning = new Alert(Alert.AlertType.WARNING);
    private static final Alert success = new Alert(Alert.AlertType.INFORMATION);
    private static final Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

    public static void warning(String message){
        warning.setHeaderText(message);
        warning.showAndWait();
    }

    public static void success(String message){
        success.setHeaderText(message);
        success.showAndWait();
    }

    public static boolean confirm(String message){
        ButtonType buttonYES = new ButtonType("Ja");
        ButtonType buttonNO = new ButtonType("Nei");

        confirm.getButtonTypes().setAll(buttonYES, buttonNO);
        confirm.setHeaderText(message);
        confirm.showAndWait();

        return confirm.getResult().getText().equals("Ja");
    }
}