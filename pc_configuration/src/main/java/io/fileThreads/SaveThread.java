package io.fileThreads;

import io.save.*;

import javafx.concurrent.Task;
import validations.ioExceptions.InvalidExtensionException;

import java.util.ArrayList;

public class SaveThread<T> extends Task<Void> {
    private String path;
    private ArrayList<T> ltw;
    public SaveThread(ArrayList<T> listToWrite, String path){
        this.path = path;
        this.ltw = listToWrite;
    }

    private SaveAbstract<T> saveBehaviorFactory() {
        String extension = this.path.substring(path.lastIndexOf("."));
        switch (extension){
            case ".csv":
                return new SaveCsvBehavior<>();
            case ".bin":
                return new SaveBinBehavior<>();
            default:
                throw new InvalidExtensionException("This file type is not supported!\nrequired types: .csv & .bin");
        }
    }

    @Override
    public Void call(){
        try { Thread.sleep(2000); }
        catch (InterruptedException ignored){}
        saveBehaviorFactory().write(this.path,this.ltw);
        return null;
    }
}
