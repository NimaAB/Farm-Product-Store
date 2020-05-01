package validations.customExceptions;

import java.io.IOException;

public class InvalidFileNameException extends IOException {
    public InvalidFileNameException(String msg){
        super(msg);
    }
}
