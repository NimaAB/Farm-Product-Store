package org.app.validation.ioExceptions;

public class InvalidExtensionException extends StringIndexOutOfBoundsException {
    public InvalidExtensionException(String msg){
        super(msg);
    }
}
