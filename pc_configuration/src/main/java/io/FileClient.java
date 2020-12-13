package io;

import io.open.OpenAbstract;
import io.save.SaveAbstract;
import validations.ioExceptions.FileDontExistsException;
import validations.ioExceptions.InvalidFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FileClient<T> {
    private String path;
    private ArrayList<T> listToWrite;
    private SaveAbstract saveBehavior;

    public FileClient(SaveAbstract saveBehavior, ArrayList<T> listToWrite, String path){
        this.saveBehavior = saveBehavior;
        this.listToWrite = listToWrite;
        this.path = path;
    }

    private OpenAbstract openBehavior;
    public FileClient(OpenAbstract openBehavior, String path){
        this.openBehavior = openBehavior;
        this.path = path;
    }

    public void save(){
        this.saveBehavior.write(this.path, this.listToWrite);
    }
    public <T> ArrayList<T> open() throws FileDontExistsException{
        File file = new File(this.path);
        if(!file.exists()) throw new FileDontExistsException("Filen med sti: " + this.path+ " finnes ikke!");
        return this.openBehavior.read(file);
    }

}
