package filehandling;

import javafx.concurrent.Task;

import java.io.File;

public abstract class ReaderAbstract extends Task<String> {

    public abstract String read(File filePath);

}
