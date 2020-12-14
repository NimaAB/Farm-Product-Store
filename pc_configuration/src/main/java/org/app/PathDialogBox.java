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
        pathDialog.setTitle("Save");
        pathDialog.setHeaderText("Where do you want to save your file?");
        pathDialog.setContentText("path: ");
        Optional<String> path = pathDialog.showAndWait();
        if(path.isPresent()){
            try{
                pathValidation(path.get());
                return path.get();
            }catch (InvalidExtensionException e){
                Alerts.warning(e.getMessage());
            }
        }
        return null;
    }

    private void pathValidation(String path) throws InvalidExtensionException{
        if(!path.contains(".")){
            throw new InvalidExtensionException("The given path doesn't include any extension!");
        }
    }
    private void fileNotFound(String path) throws  FileDontExistsException{
        if(!new File(path).exists()){
            throw  new FileDontExistsException("File  with path: \" + path+ \" not found!");
        }
    }

    public String getPathToOpen(){

        pathDialog.setTitle("Open");
        pathDialog.setHeaderText("What is the path of the file, you want open?");
        pathDialog.setContentText("path: ");
        Optional<String> path = pathDialog.showAndWait();

        if(path.isPresent()){
            try{
                pathValidation(path.get());
                fileNotFound(path.get());
                return path.get();
            }catch (FileDontExistsException | InvalidExtensionException e){
                Alerts.warning(e.getMessage());
            }
        }
        return null;
    }
}
