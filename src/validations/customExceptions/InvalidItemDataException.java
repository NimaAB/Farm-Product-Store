package validations.customExceptions;

public class InvalidItemDataException extends IllegalArgumentException {
    public InvalidItemDataException(String msg){
        super(msg);
    }
}
