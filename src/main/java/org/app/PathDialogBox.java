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
        pathDialog.setHeaderText("Gi filen et navn med .csv eller .bin som filutvidelse: ");
        pathDialog.setContentText("Filnavn: ");
        Optional<String> path = pathDialog.showAndWait();
        return path.orElse(null);
    }

    public String getPathToOpen(){
        pathDialog.setTitle("Åpne");
        pathDialog.setHeaderText("Hva heter filen du vil åpne?\nÅpne filer kun fra DataFraApp/");
        pathDialog.setContentText("Filnavn: ");
        Optional<String> path = pathDialog.showAndWait();
        return path.orElse(null);
    }

    public void extensionCheck(String path) throws InvalidExtensionException{
        if(!path.contains(".")){
            throw new InvalidExtensionException("Filen mangler en filutvidelse.");
        }
    }

    public void fileNotFound(String path) throws  FileDontExistsException{
        if(!new File(path).exists()){
            throw  new FileDontExistsException("Filen: " + path + " er ikke funnet!");
        }
    }

    public void nullPathHandling(String path) throws NullPointerException{
        if(path.isBlank()|| path.isEmpty()) {
            throw new NullPointerException("En fil er ikke gitt.");
        }
    }
}
