package validations.customExceptions;

import java.io.FileNotFoundException;

public class InvalidFileException extends FileNotFoundException {
    public InvalidFileException(String msg){
        super(msg);
    }
}
