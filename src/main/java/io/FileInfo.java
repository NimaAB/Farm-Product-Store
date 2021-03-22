package io;

public class FileInfo {
    private String path;
    public FileInfo(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    public String getExtentsion(){
        return path.substring(path.lastIndexOf("."));
    }
}
