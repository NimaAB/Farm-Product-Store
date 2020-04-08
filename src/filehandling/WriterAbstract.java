package filehandling;

import dataModels.Items;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class WriterAbstract extends Task<Void> {
    public abstract void write(ArrayList<Items> items, File filePath);
}
