package validations;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MyAlerts {
    private static final Alert warning = new Alert(Alert.AlertType.WARNING);
    private static final Alert success = new Alert(Alert.AlertType.INFORMATION);
    private static final Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

    public static void warningAlert(String message){
        warning.setHeaderText(message);
        warning.showAndWait();
    }

    public static void successAlert(String message){
        success.setHeaderText(message);
        success.showAndWait();
    }

    public static boolean confirmAlert(String message){
        ButtonType buttonYES = new ButtonType("Yes");
        ButtonType buttonNO = new ButtonType("No");

        confirm.getButtonTypes().setAll(buttonYES, buttonNO);
        confirm.setHeaderText(message);
        confirm.showAndWait();

        return confirm.getResult().getText().equals("Yes");
    }
}
