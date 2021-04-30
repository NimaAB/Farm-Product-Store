package org.validation.customExceptions;

public class InvalidTextInputException extends IllegalArgumentException {
    public InvalidTextInputException(String msg){
        super(msg);
    }
}
