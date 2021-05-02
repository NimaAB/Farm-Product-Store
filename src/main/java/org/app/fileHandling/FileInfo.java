package org.app.fileHandling;


public class FileInfo {
    private String fullPath;
    public FileInfo(String path){
        this.fullPath = path;
    }

    public String getFullPath(){
        return this.fullPath;
    }

    public String getExtension(){
        return fullPath.substring(fullPath.lastIndexOf("."));
    }

    public String getFileName(){
        return fullPath.substring("DataFraApp/".length());
    }

}
