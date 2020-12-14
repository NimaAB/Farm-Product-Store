package io;

import io.fileThreads.OpenThread;
import io.fileThreads.SaveThread;

import validations.ioExceptions.FileDontExistsException;
import validations.ioExceptions.InvalidExtensionException;

import java.util.ArrayList;

public class FileClient<T> {
    private String path;
    private ArrayList<T> listToWrite;

    public FileClient(ArrayList<T> listToWrite, String path){
        this.listToWrite = listToWrite;
        this.path = path;
    }

    public FileClient(String path){
        this.path = path;
    }

    public void save() throws FileDontExistsException, InvalidExtensionException{
        SaveThread<T> saveThread = new SaveThread<>(listToWrite,path);
        saveThread.call();
    }

    public <T> ArrayList<T> open() throws FileDontExistsException, InvalidExtensionException {
        OpenThread<T> openThread = new OpenThread<>(this.path);
        return openThread.call();
    }

}
