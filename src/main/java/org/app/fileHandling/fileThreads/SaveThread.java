package org.app.fileHandling.fileThreads;

import org.app.fileHandling.FileInfo;
import org.app.fileHandling.save.*;

import javafx.concurrent.Task;
import org.app.validation.ioExceptions.InvalidExtensionException;
import org.app.validation.ioExceptions.InvalidTypeException;

import java.util.ArrayList;

public class SaveThread<T> extends Task<Void> {
    private FileInfo file;
    ArrayList<T> listToWrite;
    public SaveThread(FileInfo file, ArrayList<T> list){
        this.file = file;
        this.listToWrite = list;
    }


    private SaveAbstract<T> saveBehaviorFactory() {
        String extension = this.file.getExtension();
        switch (extension){
            case ".csv":
                return new SaveCsvBehavior<>();
            case ".bin":
                return new SaveBinBehavior<>();
            default:
                throw new InvalidExtensionException("Filtype støttes ikke.\nLagre kun *.bin & *.csv");
        }
    }

    @Override
    public Void call() throws InvalidTypeException {
        try {
            for(int i = 0; i < 100; i++){
                Thread.sleep(20);
                updateProgress(i, 100);
            }
        }
        catch (InterruptedException ignored){}
        saveBehaviorFactory().write(this.file.getFullPath(), this.listToWrite);
        return null;
    }
}
