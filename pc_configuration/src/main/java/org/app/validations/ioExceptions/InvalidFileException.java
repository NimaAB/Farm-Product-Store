package org.app.validations.ioExceptions;

import java.io.FileNotFoundException;

public class InvalidFileException extends FileNotFoundException {
    public InvalidFileException(String msg){
        super(msg);
    }
}
