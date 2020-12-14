package io;

import validations.ioExceptions.FileDontExistsException;
import validations.ioExceptions.InvalidExtensionException;

import java.io.File;

public class Validation {
    private String path;
    public Validation(String path){
        this.path = path;
    }
    public void pathValidation() throws InvalidExtensionException, FileDontExistsException {
        if(!path.contains(".")){
            throw new InvalidExtensionException("The given path doesn't include any extension!");
        }

        File file = new File(this.path);
        if(!file.exists()){
            throw new FileDontExistsException("File  with path: " + this.path+ " not found!");
        }
    }
}
