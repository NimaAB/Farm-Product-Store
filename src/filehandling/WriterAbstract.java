package filehandling;

import dataModels.data.Items;
import javafx.concurrent.Task;

import java.io.File;
import java.util.ArrayList;

public abstract class WriterAbstract extends Task<Void> {
    public abstract void write(ArrayList<Items> items, String filePath);
}
