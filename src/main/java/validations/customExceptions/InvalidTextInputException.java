package validations.customExceptions;

public class InvalidTextInputException extends InvalidNumberException {
    public InvalidTextInputException(String msg){
        super(msg);
    }
}
