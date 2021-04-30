package org.fileHandling.fileThreads;

import org.fileHandling.FileInfo;
import org.fileHandling.open.OpenAbstract;
import org.fileHandling.open.OpenBinBehavior;
import org.fileHandling.open.OpenCsvBehavior;
import javafx.concurrent.Task;
import org.validation.ioExceptions.InvalidExtensionException;
import org.validation.ioExceptions.InvalidTypeException;


import java.io.File;
import java.util.ArrayList;

public class OpenThread<T> extends Task<ArrayList<T>> {
    private FileInfo file;

    public OpenThread(FileInfo file){
        this.file = file;
    }

    private OpenAbstract<T> openBehaviorFactory() {
        String extension = this.file.getExtension();
        switch (extension){
            case ".csv":
                return new OpenCsvBehavior<>();
            case ".bin":
                return new OpenBinBehavior<>();
            default:
                throw new InvalidExtensionException("Filtype støttes ikke.\nÅpne kun *.bin & *.csv");
        }
    }


    @Override
    public ArrayList<T> call() throws InvalidTypeException {
        try {
            for(int i = 0; i < 100; i++){
                Thread.sleep(20);
                updateProgress(i, 100);
            }
        } catch (InterruptedException ignored){}
        return openBehaviorFactory().read(new File(file.getPath()));
    }
}
