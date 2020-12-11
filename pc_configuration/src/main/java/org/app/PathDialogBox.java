package org.app;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class PathDialogBox {
    public static String getPath(String msg){
        TextInputDialog pathDialog = new TextInputDialog();
        pathDialog.setTitle("Path Dialog bBox");
        pathDialog.setHeaderText(msg);
        pathDialog.setContentText("path: ");
        Optional<String> path = pathDialog.showAndWait();
        return path.orElse(null);
    }

}
