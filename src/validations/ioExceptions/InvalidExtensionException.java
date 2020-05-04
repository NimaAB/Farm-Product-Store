package validations.ioExceptions;

public class InvalidExtensionException extends StringIndexOutOfBoundsException {
    public InvalidExtensionException(String msg){
        super(msg);
    }
}
