package filehandling.jobj;


import filehandling.ReaderAbstract;

import java.io.File;

public class ReaderJOBJ extends ReaderAbstract {
    private File filePath;
    public ReaderJOBJ(File filePath){
        this.filePath = filePath;
    }

    @Override
    public String read(File filePath) {
        return null;
    }

    @Override
    protected String call() throws Exception {
        return read(filePath);
    }
}
