package org.app.validations.customExceptions;

public class InvalidDataException extends IllegalArgumentException {
    public InvalidDataException(String msg){
        super(msg);
    }
}
