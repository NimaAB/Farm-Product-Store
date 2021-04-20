package org.app;

import javafx.scene.control.TextInputDialog;
import validations.Alerts;
import validations.ioExceptions.FileDontExistsException;
import validations.ioExceptions.InvalidExtensionException;

import java.io.File;
import java.util.Optional;

public class PathDialogBox {

    private TextInputDialog pathDialog = new TextInputDialog();

    public String getPathToSave(){
        pathDialog.setTitle("Lagre");
        pathDialog.setHeaderText("Gi filen din et navn med .csv eller .bin:");
        pathDialog.setContentText("Filnavn: ");
        Optional<String> path = pathDialog.showAndWait();
        return path.orElse(null);
    }

    public String getPathToOpen(){
        pathDialog.setTitle("Åpne");
        pathDialog.setHeaderText("Hva heter filen du vil åpne?");
        pathDialog.setContentText("Filnavn: ");
        Optional<String> path = pathDialog.showAndWait();
        return path.orElse(null);
    }

    public void extensionCheck(String path) throws InvalidExtensionException{
        if(!path.contains(".")){
            throw new InvalidExtensionException("The given path doesn't include any extension!");
        }
    }

    public void fileNotFound(String path) throws  FileDontExistsException{
        if(!new File(path).exists()){
            throw  new FileDontExistsException("File  with path: " + path + " not found!");
        }
    }

    public void nullPathHandling(String path) throws NullPointerException{
        if(path.isBlank()|| path.isEmpty()) {
            throw new NullPointerException("No path given!");
        }
    }
}
