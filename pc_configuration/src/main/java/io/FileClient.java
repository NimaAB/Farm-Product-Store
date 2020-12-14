package io;

import io.fileThreads.OpenThread;
import io.open.OpenAbstract;
import io.save.SaveAbstract;
import validations.ioExceptions.FileDontExistsException;
import validations.ioExceptions.InvalidExtensionException;
import validations.ioExceptions.InvalidFileException;

import java.io.File;
import java.io.FileNotFoundException;
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

    public void save(){

    }

    public <T> ArrayList<T> open() throws FileDontExistsException, InvalidExtensionException {
        OpenThread<T> openThread = new OpenThread<>(this.path);
        return openThread.call();
    }

}
