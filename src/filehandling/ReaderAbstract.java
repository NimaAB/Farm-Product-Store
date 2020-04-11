package filehandling;

import dataModels.data.Items;
import exceptions.dataExceptions.InvalidItemDataException;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ReaderAbstract extends Task<ArrayList<Items>> {
    protected abstract ArrayList<Items> read(File filePath) throws IOException, InvalidItemDataException;
}
