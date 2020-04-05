package filehandling;

import javafx.concurrent.Task;

import java.io.File;
import java.util.ArrayList;

public abstract class WriterAbstract extends Task<Void> {
    protected abstract <E> void write(ArrayList<E> objects, File filePath);
}
