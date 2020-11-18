package filehandling;

import javafx.concurrent.Task;
import java.util.ArrayList;

public abstract class WriterAbstract <T> extends Task<T> {
    protected abstract void write(ArrayList<T> items, String filePath);
}
