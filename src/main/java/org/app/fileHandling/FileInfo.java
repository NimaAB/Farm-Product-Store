package org.app.fileHandling;

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
}
