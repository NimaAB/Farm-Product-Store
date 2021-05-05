package org.app.validation.customExceptions;

public class EmptyFieldException extends NullPointerException{
    public EmptyFieldException(String msg){
        super(msg);
    }
}
