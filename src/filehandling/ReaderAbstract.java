package filehandling;

import dataModels.data.Components;
import validations.customExceptions.InvalidItemDataException;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ReaderAbstract extends Task<ArrayList<Components>> {
    protected abstract ArrayList<Components> read(File filePath) throws IOException, InvalidItemDataException;
}
