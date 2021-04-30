package org.app.validation.customExceptions;

public class InvalidNumberFormat extends NumberFormatException{
    public InvalidNumberFormat(String msg){
        super(msg);
    }
}
