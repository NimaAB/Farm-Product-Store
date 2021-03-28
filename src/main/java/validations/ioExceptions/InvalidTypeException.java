package validations.ioExceptions;

public class InvalidTypeException extends ClassNotFoundException {
    public InvalidTypeException(String msg){
        super(msg);
    }
}
