package io;

import io.fileThreads.OpenThread;
import io.fileThreads.SaveThread;


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

    public void save() {
        SaveThread<T> saveThread = new SaveThread<>(this.listToWrite,this.path);
        saveThread.call();
    }

    public <T> ArrayList<T> open() {
        OpenThread<T> openThread = new OpenThread<>(this.path);
        return openThread.call();
    }
}
