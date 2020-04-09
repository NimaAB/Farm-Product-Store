package exceptions.dataExceptions;

public class InvalidNumberException extends NumberFormatException {
    public InvalidNumberException(String msg){
        super(msg);
    }
}
