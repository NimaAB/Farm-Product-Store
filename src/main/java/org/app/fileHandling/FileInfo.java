package org.app.fileHandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileInfo {
    private String path;
    public FileInfo(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    public String getExtension(){
        return path.substring(path.lastIndexOf("."));
    }

    public void deleteFile(){
        try{
            Files.deleteIfExists(Paths.get(this.path));
        }catch (IOException ignore){ }
    }

}
