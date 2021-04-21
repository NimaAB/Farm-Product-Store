package validations.customExceptions;

public class InvalidArgument extends IllegalArgumentException{
    public InvalidArgument(String msg){
        super(msg);
    }
}
