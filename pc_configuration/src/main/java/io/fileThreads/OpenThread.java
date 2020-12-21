package io.fileThreads;

import io.FileInfo;
import io.open.OpenAbstract;
import io.open.OpenBinBehavior;
import io.open.OpenCsvBehavior;
import javafx.concurrent.Task;

import validations.ioExceptions.InvalidExtensionException;

import java.io.File;
import java.util.ArrayList;

public class OpenThread<T> extends Task<ArrayList<T>> {
    private FileInfo file;

    public OpenThread(FileInfo file){
        this.file = file;
    }

    private OpenAbstract<T> openBehaviorFactory() {
        String extension = this.file.getExtentsion();
        switch (extension){
            case ".csv":
                return new OpenCsvBehavior<>();
            case ".bin":
                return new OpenBinBehavior<>();
            default:
                throw new InvalidExtensionException("This file type is not supported!\nrequired types: .csv & .bin");
        }
    }

    @Override
    public ArrayList<T> call() {
        try { Thread.sleep(2000); }
        catch (InterruptedException ignored){}
        return openBehaviorFactory().read(new File(file.getPath()));
    }
}
