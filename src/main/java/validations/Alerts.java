package validations;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    public synchronized static <V> Alert showLoadingDialog(Task<V> task, String dialogMessage){
        // Children
        ProgressBar p = new ProgressBar();
        p.progressProperty().bind(task.progressProperty());
        p.setPrefWidth(300);
        p.setPrefHeight(15);
        Label label = new Label("Thread running...");
        label.setStyle("-fx-padding: 10px 0 0 0; -fx-font-size: 14px; -fx-text-fill: #2f5d62");

        // Parent
        VBox box = new VBox();
        box.getChildren().addAll(p, label);

        // Alert Dialog
        ButtonType ok = new ButtonType("Ok");
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setHeaderText(dialogMessage);
        alert.getButtonTypes().setAll(ok);
        alert.setTitle("Loading");
        alert.getDialogPane().setContent(box);
        alert.getDialogPane().setPrefWidth(300);
        alert.getDialogPane().setPrefHeight(175);
        alert.getDialogPane().lookupButton(ok).setDisable(true);

        Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
        s.setOnCloseRequest(Event::consume);

        return alert;
    }

}