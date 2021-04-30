package org.fileHandling.fileThreads;

import org.fileHandling.FileInfo;
import org.fileHandling.save.*;

import javafx.concurrent.Task;
import org.validation.ioExceptions.InvalidExtensionException;

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
    public Void call(){
        try {
            for(int i = 0; i < 100; i++){
                Thread.sleep(20);
                updateProgress(i, 100);
            }
        }
        catch (InterruptedException ignored){}
        saveBehaviorFactory().write(this.file.getPath(), this.listToWrite);
        return null;
    }
}
