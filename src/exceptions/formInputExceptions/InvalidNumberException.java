package exceptions.formInputExceptions;

public class InvalidNumberException extends NumberFormatException {
    public InvalidNumberException(String msg){
        super(msg);
    }
}
