package org.app.validations.ioExceptions;

import java.io.IOException;

public class InvalidFileNameException extends IOException {
    public InvalidFileNameException(String msg){
        super(msg);
    }
}
