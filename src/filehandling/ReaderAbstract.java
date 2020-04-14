package filehandling;

import validations.customExceptions.InvalidItemDataException;
import javafx.concurrent.Task;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ReaderAbstract <T> extends Task<ArrayList<T>> {
    protected abstract ArrayList<T> read(String filePath) throws IOException, InvalidItemDataException;

}
