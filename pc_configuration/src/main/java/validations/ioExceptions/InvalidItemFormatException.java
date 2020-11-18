package validations.ioExceptions;

import java.io.IOException;

public class InvalidItemFormatException extends IOException {
    public InvalidItemFormatException(String msg){
        super(msg);
    }
}
