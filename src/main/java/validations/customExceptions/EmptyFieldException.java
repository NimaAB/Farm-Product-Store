package validations.customExceptions;

public class EmptyFieldException extends NullPointerException{
    public EmptyFieldException(String msg){
        super(msg);
    }
}
